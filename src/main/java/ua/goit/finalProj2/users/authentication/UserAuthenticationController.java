package ua.goit.finalProj2.users.authentication;

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
import ua.goit.finalProj2.users.form_common.UserDAO;


@Controller
@RequestMapping("/users/authentication")
@AllArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private final UserService service;

    @GetMapping
    public String get_authentication(Model model){
        model.addAttribute("user", new UserDAO());
        return "authentication";
    }

    @PostMapping
    public String post_authentication(@ModelAttribute UserDAO userDAO, Model model){

        try {
            User user = service.getUserByEmail(userDAO);
        } catch (AuthenticationException e){
            model.addAttribute("error", e.getMessage());
            return "authentication";
        }

        return "/";
    }
}
