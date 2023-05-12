package ua.goit.finalProj2.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserRole;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("admin")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
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

    @PostMapping("/admin/change-role")
    public String changeUserRole(@RequestParam("username") String username,
                                 @RequestParam("role") UserRole role) {
        User user = userRepository.findUserByUsername(username).get();
        System.out.println(user.getEmail());
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}