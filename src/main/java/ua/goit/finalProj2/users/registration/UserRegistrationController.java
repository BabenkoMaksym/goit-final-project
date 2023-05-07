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
import ua.goit.finalProj2.users.form_common.AuthenticationException;
import ua.goit.finalProj2.users.form_common.UserDto;


@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class UserRegistrationController {


    @Autowired
    private final UserService userService;

    @GetMapping
    public String getRegistration(Model model){
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping
    public String postRegistration(@ModelAttribute UserDto userDto, Model model){

        System.out.println("reg");
        try {
            userService.createUser(userDto);
        } catch (AuthenticationException e){
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        return "redirect:/users/login";
    }
}
