package ua.goit.finalProj2.users.form_common;

public class UserValidate {

    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{5,50}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static void validateUserAuthentication(UserDAO userDAO) throws AuthenticationException{
        checkEmailValid(userDAO.getEmail());
        checkPasswordValid(userDAO.getPassword());
    }

    public static void validateUserRegister(UserDAO userDAO) throws AuthenticationException{
        checkUsernameValid(userDAO.getUsername());
        checkEmailValid(userDAO.getEmail());
        checkPasswordValid(userDAO.getPassword());
    }

    private static void checkUsernameValid(String username) throws AuthenticationException{
        if(!username.matches(USERNAME_REGEX))
            throw new AuthenticationException("Username is invalid.");
    }

    private static void checkEmailValid(String email) throws AuthenticationException{
        if(!email.matches(EMAIL_REGEX))
            throw new AuthenticationException("Email is invalid.");
    }

    private static void checkPasswordValid(String password) throws AuthenticationException{
        if(!password.matches(PASSWORD_REGEX))
            throw new AuthenticationException("Password is invalid.");
    }
}
