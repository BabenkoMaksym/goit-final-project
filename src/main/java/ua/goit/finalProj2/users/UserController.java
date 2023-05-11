package ua.goit.finalProj2.users;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static ua.goit.finalProj2.users.form_common.UserValidate.*;

@Data
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        User user;
        try {
            user = userService.getUserByUsername(userDto);
            user.setPassword(null);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "changePassword";
    }


    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String username,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setPassword(oldPassword);
            User user = userService.getUserByUsername(userDto);
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new AuthenticationException("Неправильний старий пароль!");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new AuthenticationException("Новий пароль не збігаєтся!");
            }
            checkPasswordValid(newPassword);
            userDto.setPassword(newPassword);
            userService.changePassword(userDto);
        } catch (AuthenticationException e) {
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "changePassword";
        }
        return "redirect:/profile";
    }

    @GetMapping("/changeEmail")
    public String showChangeEmailForm(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "changeEmail";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(@RequestParam String username, @RequestParam String newEmail, @RequestParam String password, Model model) {
        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setPassword(password);
            User user = userService.getUserByUsername(userDto);
            checkEmailValid(newEmail);
            userDto.setEmail(newEmail);
            userService.changeEmail(userDto);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthenticationException("Неправильний пароль!");
            }
        } catch (AuthenticationException e) {
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "changeEmail";
        }
        return "redirect:/profile";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
