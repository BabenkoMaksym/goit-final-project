package ua.goit.finalProj2.notes.keyWords;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.notes.Note;

import javax.persistence.*;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "key_words", schema = "final_project")
public class KeyWords {
    @Id
    @Column(name = "word")
    String word;

    @ManyToOne
    @JoinColumn(name = "note_id")
    @ToString.Exclude
    Note note;
}
