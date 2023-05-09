package ua.goit.finalProj2.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/mynote")
public class UserNotesController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;


    @GetMapping
    public String showNotes(Model model, Authentication authentication) throws AuthenticationException {
        User user = userService.authorizeUser(authentication.isAuthenticated());
        List<Note> notes = noteService.listOfNotesByUser(user);
        model.addAttribute("notes", notes);
        return "list-user-note";
    }

    @PostMapping
    public String saveOrUpdateNote(@RequestParam(required = false) UUID id,
                                   @RequestParam String name,
                                   @RequestParam String content,
                                   @RequestParam NoteType noteType,
                                   @RequestParam LocalDateTime createdAt,
                                   Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Note note;
        if (id == null) {
            note = new Note();
            note.setUser(user);
        } else {
            note = noteService.getById(id);
        }
        if (note == null) {
            return "redirect:/mynotes";
        }
        note.setName(name);
        note.setContent(content);
        note.setNoteType(noteType);
        note.setUser(user);
        note.setCreatedAt(createdAt);
        noteService.add(note);
        return "redirect:/mynotes";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam UUID id) {
        noteService.deleteById(id);
        return "redirect:/mynotes";
    }
}


