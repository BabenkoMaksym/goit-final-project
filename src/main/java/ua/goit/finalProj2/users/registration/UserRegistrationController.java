package ua.goit.finalProj2.users.registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.UserDAO;

@Controller
@RequestMapping("/users/registration")
@AllArgsConstructor
public class UserRegistrationController {


    @Autowired
    private final UserService service;

    @GetMapping
    public String get_registration(Model model){
        model.addAttribute("user", new UserDAO());
        return "registration";
    }

    @PostMapping
    public String post_registration(@ModelAttribute UserDAO userDAO, Model model){

        try {
            service.createUser(userDAO);
        } catch (RegistrationException e){
            model.addAttribute("error", e.getMessage());
            return "/users/registration";
        }

        return "redirect:/users/login";
    }
}
