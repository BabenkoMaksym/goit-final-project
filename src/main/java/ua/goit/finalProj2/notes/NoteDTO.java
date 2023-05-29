package ua.goit.finalProj2.notes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.comments.Comment;
import ua.goit.finalProj2.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteDTO {


    UUID id;
    String name;
    String content;
    String keyWords;
    NoteType noteType;
    User user;
    LocalDateTime createdAt;
    List<Comment> comments;
}
