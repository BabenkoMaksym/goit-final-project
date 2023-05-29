package ua.goit.finalProj2.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.goit.finalProj2.users.UserRole;

import java.util.UUID;

@Controller
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/notes/comments/delete")
    public String deleteComment(@RequestParam("id") UUID id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {

        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            redirectAttributes.addFlashAttribute("error", "Comment not found.");
            return "redirect:/notes/";
        }
        String currentUsername = authentication.getName();
        String commentOwnerUsername = comment.getUser().getUsername();
        boolean isAdmin = comment.getUser().getRole().equals(UserRole.ADMIN);

        if (isAdmin) {
            commentRepository.deleteById(id);
        } else if (!currentUsername.equals(commentOwnerUsername)) {
            isAdmin = false;
        }
        model.addAttribute("isAdmin", isAdmin);
        System.out.println("Comment username: " + comment.getUser().getUsername());
        return "redirect:/notes/";
    }


}
