package ua.goit.finalProj2.notes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.goit.finalProj2.notes.entity.Note;
import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository<Note, UUID> {
}
