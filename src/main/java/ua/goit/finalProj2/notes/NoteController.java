package ua.goit.finalProj2.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.finalProj2.notes.form_common.NoteCreateException;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/edit")
    public String showNoteForm(@RequestParam(name = "id", required = false) UUID id, Model model) {
        Note note = new Note();
        if (id != null) {
            note = noteService.getById(id);
        }
        model.addAttribute("note", note);
        return "notes/edit";
    }

    @PostMapping("/edit")
    public String saveOrUpdateNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        String name = authentication.getName();
        note.setCreatedAt(LocalDateTime.now());
        note.setUser(userRepository.findUserByUsername(name).get());
        try {
            if (note.getId() == null) {
                noteService.add(note);
            } else {
                noteService.update(note);
            }
        } catch (NoteCreateException e) {
            model.addAttribute("error", e.getMessage());
            return "notes/edit";
        }
        model.addAttribute("note", note);
        return "notes/created";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam("id") UUID id, Model model) {
        if (id != null) {
            try {
                noteService.deleteById(id);
            } catch (IllegalArgumentException e) {
                model.addAttribute("deleteMsg", e.getMessage());
            }
        }
        return "redirect:/notes/my";
    }

    @GetMapping("/")
    public String feedNotes(@RequestParam(name = "page", required = false) Integer page, Model model) {
        page = page == null ? 0 : page >= 1 ? page - 1 : page;
        List<Note> notes = noteService.listPublicNotes(page);
        model.addAttribute("notes", notes);
        return "feed";
    }

    @GetMapping("/read")
    public String readNote(@RequestParam("id") UUID id, Model model, Authentication authentication) {
        Note note = noteService.getById(id);
        if (note.getNoteType() == NoteType.PRIVATE && !authentication.getName().equals(note.getUser().getUsername())) {
            return "redirect:/notes/notfound";
        }
        model.addAttribute("note", note);
        return "notes/read";

    }

    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        String name = authentication.getName();
        note.setId(UUID.randomUUID());
        note.setUser(userRepository.findUserByUsername(name).get());
        note.setCreatedAt(LocalDateTime.now());

        try {
            noteService.add(note);
        } catch (NoteCreateException e) {
            model.addAttribute("error", e.getMessage());
            return "/notes/create";
        }
        model.addAttribute("note", note);
        return "notes/created";
    }

    @GetMapping("/my")
    public String showAllUserNotes(Model model, Authentication authentication) throws AuthenticationException {
        User user = userService.authorizeUser(authentication.isAuthenticated());
        List<Note> notes = noteService.listOfNotesByUser(user);
        model.addAttribute("notes", notes);
        return "notes/my";
    }

    @GetMapping("/notfound")
    public String noteNotFound() {
        return "noteNotFound";
    }

}
