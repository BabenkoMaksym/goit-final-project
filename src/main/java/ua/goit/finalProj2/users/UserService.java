package ua.goit.finalProj2.users;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDAO;

import static ua.goit.finalProj2.users.form_common.UserValidate.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    public void createUser(UserDAO userDAO) throws AuthenticationException{
        validateUserAuthentication(userDAO);
        userDAO.setPassword(passwordEncoder.encode(userDAO.getPassword()));
        repository.create(userDAO);
    }

    public User getUserByEmail(UserDAO userDAO) throws AuthenticationException{
        validateUserRegister(userDAO);
        userDAO.setPassword(passwordEncoder.encode(userDAO.getPassword()));
        return repository.getUserByEmail(userDAO);
    }
}
