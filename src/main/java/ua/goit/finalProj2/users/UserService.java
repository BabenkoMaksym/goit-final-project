package ua.goit.finalProj2.users;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;

import static ua.goit.finalProj2.users.form_common.UserValidate.validateUserRegister;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    public void createUser(UserDto userDto) throws AuthenticationException {
        User user = repository.findUserByUsername(userDto.getUsername()).orElse(null);
        if (user != null) {
            throw new AuthenticationException("Користувач з таким логіном вже існує.");
        } else {
            user = new User();
            validateUserRegister(userDto);
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(UserRole.USER);
            user.setEnabled(true);
            repository.create(user);
        }
    }

    public User getUserByUsername(UserDto userDto) throws AuthenticationException {
        User user = repository.findUserByUsername(userDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Invalid username"));
        return user;
    }

    public void changePassword(UserDto userDto) {
        User user;
        try {
            user = repository.findUserByUsername(userDto.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Invalid username"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        repository.save(user);
    }

    public void changeEmail(UserDto userDto) {
        User user;
        try {
            user = repository.findUserByUsername(userDto.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Invalid username"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        user.setEmail(userDto.getEmail());
        repository.save(user);
    }
}

