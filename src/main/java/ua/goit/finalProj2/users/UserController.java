package ua.goit.finalProj2.users;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Data
@Controller
public class UserController {

    private final UserService userService;

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

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String username, @RequestParam String password) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userService.changePassword(userDto);
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
