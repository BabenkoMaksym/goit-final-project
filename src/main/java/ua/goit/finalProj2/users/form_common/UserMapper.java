package ua.goit.finalProj2.users.form_common;

import ua.goit.finalProj2.users.User;

public class UserMapper {

    public static User toUser(UserDAO userDAO){
        User user = new User();

        user.setUsername(userDAO.getUsername());
        user.setEmail(userDAO.getEmail());
        user.setPassword(userDAO.getPassword());

        return user;
    }
}
