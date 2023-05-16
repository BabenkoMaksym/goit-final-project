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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.goit.finalProj2.users.form_common.UserValidate.checkEmailValid;
import static ua.goit.finalProj2.users.form_common.UserValidate.checkPasswordValid;

@Data
@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String showProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user;
        user = userService.findByUsername(username);
        user.setPassword(null);
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
            User user = userService.findByUsername(username);
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new AuthenticationException("The old password is incorrect!");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new AuthenticationException("New password does not match in two fields!");
            }
            checkPasswordValid(newPassword);
            userDto.setPassword(newPassword);
            userService.changePassword(userDto);
        } catch (AuthenticationException e) {
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "changePassword";
        }
        return "redirect:/profile/";
    }

    @GetMapping("/changeEmail")
    public String showChangeEmailForm(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "changeEmail";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(@RequestParam String username,
                              @RequestParam String newEmail,
                              @RequestParam String password,
                              Model model) {

        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setPassword(password);
            User user = userService.findByUsername(username);
            checkEmailValid(newEmail);
            userDto.setEmail(newEmail);
            userService.changeEmail(userDto);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthenticationException("Wrong password!");
            }
        } catch (AuthenticationException e) {
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "changeEmail";
        }
        return "redirect:/profile/";
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