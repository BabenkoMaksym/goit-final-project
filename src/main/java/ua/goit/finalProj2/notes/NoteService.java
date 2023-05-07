package ua.goit.finalProj2.notes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.notes.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteService {
    final NoteRepository repository;

    public List<Note> listAll() {
        List<Note> notes = new ArrayList<>();
        repository.findAll().forEach(notes::add);
        return notes;
    }

    public Note add(Note note) {
        return repository.save(note);
    }

    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Note with id " + id + " does not exist");
        }
        repository.deleteById(id);
    }

    public void update(Note note) {
        if (!repository.existsById(note.getId())) {
            throw new IllegalArgumentException("Note with id " + note.getId() + " does not exist");
        }
        repository.save(note);
    }

    public Note getById(UUID id) {
        Optional<Note> optionalNote = repository.findById(id);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            throw new IllegalArgumentException("Note with id " + id + " does not exist");
        }
    }
}
