package ua.goit.finalProj2.notes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.notes.form_common.NoteCreateException;
import ua.goit.finalProj2.notes.keyWords.KeyWords;
import ua.goit.finalProj2.users.User;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



import static org.thymeleaf.util.StringUtils.join;
import static ua.goit.finalProj2.notes.form_common.NoteValidate.validateNoteCreating;

@Service
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteService {
    final NoteRepository noteRepository;

    public List<Note> listPublicNotes() {
        return noteRepository.findAll();
    }

    public Note add(Note note) throws NoteCreateException {
        validateNoteCreating(note);
        return noteRepository.save(note);
    }

    public void deleteById(UUID id) throws IllegalArgumentException{
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note with id " + id + " does not exist");
        }
        noteRepository.deleteById(id);
    }

    public void update(Note note) throws NoteCreateException {
        validateNoteCreating(note);
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
    public List<Note> listPublicNotes(Integer page){
        return noteRepository.findPublicNotes(PageRequest.of(page, 10));
    }

    public List<NoteDTO> listPublicNoteDTOs(Integer page){
        List<Note> notes = listPublicNotes(page);
        List<NoteDTO> noteDTOs = new ArrayList<>();
        for (Note note: notes) {
            noteDTOs.add(getNoteDTOFromNote(note));
        }
        return noteDTOs;
    }

    public List<Note> listOfNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }
    public List<NoteDTO> listOfNoteDTOsByUser(User user) {
      List<Note> notes =   noteRepository.findByUser(user);
      List<NoteDTO> noteDTOs= new ArrayList<>();
      for (Note note : notes){
          noteDTOs.add(getNoteDTOFromNote(note));
      }
      return noteDTOs;
    }


    public Note getNoteFromDTO(NoteDTO dto) {
        Note note = new Note();
        note.setId(dto.getId());
        note.setName(dto.getName());
        note.setNoteType(dto.getNoteType());
        note.setContent(dto.getContent());
        note.setKeyWords(transformToKeyWords(dto.getKeyWords()));
        note.setCreatedAt(dto.getCreatedAt());
        note.setUser(dto.getUser());
        return note;
    }

    public NoteDTO getNoteDTOFromNote(Note note){
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setUser(note.getUser());
        dto.setName(note.getName());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setNoteType(note.getNoteType());
        dto.setKeyWords(transformFromKeyWords(note.getKeyWords()));
        return dto;
    }
    private List<KeyWords> transformToKeyWords(String line){
       List<KeyWords> list = new ArrayList<KeyWords>();
       String[] words = line.split(", ");
        for (String word : words) {
            KeyWords keyWords = new KeyWords();
            if(word.isBlank())continue;
            keyWords.setWord(word);
            list.add(keyWords);
        }
        return list;
    }
    private String transformFromKeyWords(List<KeyWords> words){
        StringBuilder line = new StringBuilder();
        for (KeyWords word: words){
            line.append(word.getWord()).append(", ");
        }
        return line.toString();
    }

    public void copyNoteLinkToClipboard(Note note) {
        StringSelection stringSelection = new StringSelection(getNoteLink(note));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public String getNoteLink(Note note) {
        String baseUrl = "http://localhost:9999/notes/share/";
        return baseUrl + note.getId();
    }
}