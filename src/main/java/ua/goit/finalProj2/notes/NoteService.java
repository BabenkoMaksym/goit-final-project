package ua.goit.finalProj2.notes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteService {
    final NoteRepository noteRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(UUID id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note with id " + id + " does not exist");
        }
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        if (!noteRepository.existsById(note.getId())) {
            throw new IllegalArgumentException("Note with id " + note.getId() + " does not exist");
        }
        noteRepository.save(note);
    }

    public Note getById(UUID id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            throw new IllegalArgumentException("Note with id " + id + " does not exist");
        }
    }
    public List<Note> feedNote (int page){
        return noteRepository.feedNotes(page*10);
    }
}
