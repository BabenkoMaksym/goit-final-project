package ua.goit.finalProj2.notes;

import lombok.*;
import lombok.experimental.FieldDefaults;

import ua.goit.finalProj2.notes.keyWords.KeyWords;
import ua.goit.finalProj2.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "note_id")
    List<KeyWords> keyWords = new ArrayList<>();

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