package ua.goit.finalProj2.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/notes/comments/delete")
    public String deleteComment(@RequestParam("id") UUID id) {
        commentRepository.deleteById(id);
        return "redirect:/notes/";
    }


}
