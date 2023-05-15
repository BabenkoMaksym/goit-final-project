package ua.goit.finalProj2.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.goit.finalProj2.notes.Note;
import ua.goit.finalProj2.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }


}
