package ua.goit.finalProj2.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;
import ua.goit.finalProj2.users.form_common.UserValidate;


public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).get();

        return new UserPrincipal(user);
    }

//    @Override
//    public User registerNewUser(UserDto userDto) {
//        try {
//            UserValidate.validateUserRegister(userDto);
//        } catch (AuthenticationException e) {
//            throw new RuntimeException(e);
//        }
//        User user = new User();
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setRole(UserRole.USER);
//        user.setEnabled(true);
//
//        return userRepository.save(user);
//    }


}
