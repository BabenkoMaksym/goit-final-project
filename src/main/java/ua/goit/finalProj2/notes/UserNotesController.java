package ua.goit.finalProj2.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/note")
public class UserNotesController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public String showAllUserNotes(Model model, Authentication authentication) throws AuthenticationException {
        User user = userService.authorizeUser(authentication.isAuthenticated());
        List<Note> notes = noteService.listOfNotesByUser(user);
        model.addAttribute("notes", notes);
        return "list-user-note";
    }

    @GetMapping("/create")
    public String showNoteCreationPage() {
        return "create-user-notes";
    }

    @PostMapping("/create")
    public String saveNote(@RequestParam(required = false) UUID id,
                           @RequestParam String name,
                           @RequestParam String content,
                           @RequestParam(required = false, defaultValue = "PRIVATE") NoteType noteType,
                           @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime createdAt,
                           Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Note note = null;
        if (id == null) {
            note = new Note();
            note.setUser(user);
        }

        if (note == null) {
            return "redirect:/note/list";
        }

        note.setId(UUID.randomUUID());
        note.setName(name);
        note.setContent(content);
        note.setNoteType(noteType);
        note.setCreatedAt(createdAt);
        noteService.add(note);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String showNoteEditingPage(@RequestParam("id") UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "edit-user-notes";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note) {
        noteService.update(note);
        return "redirect:/note/list";
    }

    @PostMapping("/delete")
    public String deleteNoteById(@RequestParam("id") UUID id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
}