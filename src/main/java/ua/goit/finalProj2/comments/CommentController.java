package ua.goit.finalProj2.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.goit.finalProj2.notes.Note;

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

    public void addComment(Note note, int userID, String content) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.getNote().setId(note.getId());
        comment.setUserID(userID);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByNote(Note note) {
        return commentRepository.findByNoteID(note.getId());
    }
}
