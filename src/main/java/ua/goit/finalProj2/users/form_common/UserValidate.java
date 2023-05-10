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
            throw new AuthenticationException("Не валідний логін." +
                    " Допустиме ім'я користувача - будь-які символи латиниці та цифри." +
                    " Довжина імені – від 5 до 50 символів включно.");
    }

    public static void checkEmailValid(String email) throws AuthenticationException{
        if(!email.matches(EMAIL_REGEX))
            throw new AuthenticationException("Не валідний Email.");
    }

    public static void checkPasswordValid(String password) throws AuthenticationException{
        if(!password.matches(PASSWORD_REGEX))
            throw new AuthenticationException("Не валідний пароль." +
                    " Пароль користувача включає будь-які символи від 8 до 100 символів включно.");
    }
}
