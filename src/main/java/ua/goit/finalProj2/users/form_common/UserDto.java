package ua.goit.finalProj2.users.form_common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.users.User;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String username;
    String email;
    String password;
}
