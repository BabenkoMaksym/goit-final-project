package ua.goit.finalProj2.users;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.users.form_common.UserDAO;
import ua.goit.finalProj2.users.registration.RegistrationException;

import static ua.goit.finalProj2.users.form_common.UserMapper.toUser;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;

    public void createUser(UserDAO userDAO) throws RegistrationException {
        repository.save(toUser(userDAO));
    }
}
