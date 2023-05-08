package ua.goit.finalProj2.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.notes.Note;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_project", name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
    String email;
    @Enumerated(EnumType.STRING)
    UserRole role;
    boolean enabled;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    List<Note> notes;

}
