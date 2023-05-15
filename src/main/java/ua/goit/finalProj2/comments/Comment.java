package ua.goit.finalProj2.comments;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.notes.Note;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comments", schema = "final_project")
public class Comment {

    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    Note note;

    @Column(name = "user_id")
    int userID;

    @Column(name = "comment_text")
    String content;

    @Column(name = "created_at")
    LocalDateTime createdAt;

}