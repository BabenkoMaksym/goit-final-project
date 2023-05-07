package ua.goit.finalProj2.users;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;
import ua.goit.finalProj2.users.form_common.UserValidate;

import static ua.goit.finalProj2.users.form_common.UserValidate.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;



    public void createUser(UserDto userDto) throws AuthenticationException{
        validateUserRegister(userDto);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        repository.save(user);
    }

    public User getUserByEmail(UserDto userDto) throws AuthenticationException{
        validateUserRegister(userDto);
//        userDto.setPassword(passwordEncoder().encode(userDto.getPassword()));
        return repository.getUserByUsername(userDto);
    }

}
