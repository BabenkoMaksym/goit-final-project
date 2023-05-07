package ua.goit.finalProj2.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.goit.finalProj2.notes.Note;
import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository<Note, UUID> {
}
