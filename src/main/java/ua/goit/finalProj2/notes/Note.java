package ua.goit.finalProj2.notes;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import ua.goit.finalProj2.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notes", schema = "final_project")
public class Note {

    @Id
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "content")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    NoteType noteType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user;

    @Column(name = "created_at")
    LocalDateTime createdAt;

}