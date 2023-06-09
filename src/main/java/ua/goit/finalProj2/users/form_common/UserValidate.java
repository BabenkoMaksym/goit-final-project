package ua.goit.finalProj2.users.form_common;


public class UserValidate {

    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{5,50}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX = "^.{8,100}$";


    public static void validateUserRegister(UserDto userDto) throws AuthenticationException{
        checkUsernameValid(userDto.getUsername());
        checkEmailValid(userDto.getEmail());
        checkPasswordValid(userDto.getPassword());
    }

    private static void checkUsernameValid(String username) throws AuthenticationException{
        if(!username.matches(USERNAME_REGEX))
            throw new AuthenticationException("Invalid login." +
                    " Valid username - any Latin characters and numbers." +
                    " The length of the name is from 5 to 50 characters inclusive.");
    }

    public static void checkEmailValid(String email) throws AuthenticationException{
        if(!email.matches(EMAIL_REGEX))
            throw new AuthenticationException("Invalid email.");
    }

    public static void checkPasswordValid(String password) throws AuthenticationException{
        if(!password.matches(PASSWORD_REGEX))
            throw new AuthenticationException("Password is not valid." +
                    " The user password includes any characters between 8 and 100 characters inclusive.");
    }
}
