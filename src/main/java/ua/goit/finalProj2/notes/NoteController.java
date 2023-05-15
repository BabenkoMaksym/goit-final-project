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
import ua.goit.finalProj2.users.UserRole;
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
    public String showNoteForm(@RequestParam(name = "id", required = false) UUID id, Model model, Authentication authentication) {
        Note note = new Note();
        if (id != null) {
            note = noteService.getById(id);
        }
        if(note == null){
            return "redirect:/notes/notfound";
        }
        if (!authentication.getName().equals(note.getUser().getUsername())) {
            return "redirect:/notes/forbidden";
        }

        NoteDTO noteDTO = noteService.getNoteDTOFromNote(note);
        model.addAttribute("note", noteDTO);
        return "notes/edit";
    }

    @PostMapping("/edit")
    public String saveOrUpdateNote(@ModelAttribute("note") NoteDTO noteDTO, Model model, Authentication authentication) {
        String name = authentication.getName();
        noteDTO.setCreatedAt(LocalDateTime.now());
        noteDTO.setUser(userRepository.findUserByUsername(name).get());
        Note note = noteService.getNoteFromDTO(noteDTO);
        if (name.equals(note.getUser().getUsername())) {
            try {
                if (noteDTO.getId() == null) {
                    noteService.add(note);
                } else {
                    noteService.update(note);
                }

            } catch (NoteCreateException e) {
                model.addAttribute("error", e.getMessage());
                return "notes/edit";
            }
        } else {
            return "redirect:/notes/forbidden";
        }

        model.addAttribute("note", noteDTO);
        return "notes/created";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam("id") UUID id, Model model, Authentication authentication) {
        if (id != null) {
            try {
                Note note = noteService.getById(id);
                if(note == null){
                    return "redirect:/notes/notfound";
                }
                if (!authentication.getName().equals(note.getUser().getUsername())) {
                    return "redirect:/notes/forbidden";
                }
                noteService.deleteById(id);
            } catch (IllegalArgumentException e) {
                model.addAttribute("deleteMsg", e.getMessage());
            }
        }
        return "redirect:/notes/my";
    }

    @GetMapping("/")
    public String feedNotes(@RequestParam(name = "page", required = false) Integer page, Model model, Authentication authentication) {
        String name = authentication.getName();
        User user = userRepository.findUserByUsername(name).get();
        page = page == null ? 0 : page >= 1 ? page - 1 : page;
        List<NoteDTO> noteDTOs = noteService.listPublicNoteDTOs(page);
        if (user.getRole() == UserRole.ADMIN) {
            model.addAttribute("isAdmin", "admin");
        }
        model.addAttribute("notes", noteDTOs);
        return "notes/feed";
    }

    @GetMapping("/read")
    public String readNote(@RequestParam("id") UUID id, Model model, Authentication authentication) {
        NoteDTO noteDTO = noteService.getNoteDTOFromNote(noteService.getById(id));
        if (noteDTO.getNoteType() == NoteType.PRIVATE && !authentication.getName().equals(noteDTO.getUser().getUsername())) {
            return "redirect:/notes/forbidden";
        }

        model.addAttribute("note", noteDTO);
        return "notes/read";

    }

    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new NoteDTO());
        return "notes/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") NoteDTO noteDTO, Model model, Authentication authentication) {

        String name = authentication.getName();
        noteDTO.setId(UUID.randomUUID());
        noteDTO.setUser(userRepository.findUserByUsername(name).get());
        noteDTO.setCreatedAt(LocalDateTime.now());
        Note note = noteService.getNoteFromDTO(noteDTO);
        System.out.println("note = " + note);
        try {
            noteService.add(note);
        } catch (NoteCreateException e) {
            model.addAttribute("error", e.getMessage());
            return "/notes/create";
        }
        model.addAttribute("note", noteDTO);
        return "notes/created";
    }

    @GetMapping("/my")
    public String showAllUserNotes(Model model, Authentication authentication) throws AuthenticationException {
        User user = userService.authorizeUser(authentication.isAuthenticated());
        List<NoteDTO> notes = noteService.listOfNoteDTOsByUser(user);
        model.addAttribute("notes", notes);
        return "notes/my";
    }

    @GetMapping("/notfound")
    public String noteNotFound() {
        return "notes/noteNotFound";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        return "noteForbidden";
    }

    @GetMapping("/share")
    public String share(@RequestParam("id") UUID id, Model model, Authentication authentication) {
        Note note = noteService.getById(id);
        if (note.getNoteType() == NoteType.PRIVATE && !authentication.getName().equals(note.getUser().getUsername())) {
            return "redirect:/notes/notfound";
        }
        model.addAttribute("note", note);
        return "notes/share";
    }

    @PostMapping("/share")
    public void saveNoteToClipboard(@RequestParam UUID id) {
        Note note = noteService.getById(id);
        noteService.copyNoteLinkToClipboard(note);
    }
}
