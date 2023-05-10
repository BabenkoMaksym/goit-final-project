package ua.goit.finalProj2.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserRole;
import ua.goit.finalProj2.users.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).get();
        if (!user.getRole().equals(UserRole.ADMIN)) {
            return "error";
        }
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }
}