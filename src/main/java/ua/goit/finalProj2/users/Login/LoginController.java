package ua.goit.finalProj2.users.Login;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;


@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public String getLogin(Model model){
        return "login";
    }

    @PostMapping
    public String postLogin(@ModelAttribute UserDto userDto, Model model){

        System.out.println(userDto);

        try {
            User user = userService.getUserByEmail(userDto);
        } catch (AuthenticationException e){
            model.addAttribute("error", e.getMessage());
            return "login";
        }

        return "/";
    }
}
