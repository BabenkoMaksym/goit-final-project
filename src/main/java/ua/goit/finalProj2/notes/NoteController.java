package ua.goit.finalProj2.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }
    @Autowired
    public void setUserService(UserService userService) {this.userService = userService;}

    @GetMapping("/edit")
    public ModelAndView showNoteForm(@RequestParam(name = "id", required = false) UUID id) {
        ModelAndView result = new ModelAndView("note/edit");
        Note note = new Note();
        if (id != null) {
            note = noteService.getById(id);
        }
        result.addObject("note", note);
        return result;
    }

    @PostMapping("/note/edit")
    public String saveOrUpdateNote(@ModelAttribute("note") Note note) {
        if (note.getId() == null) {
            noteService.add(note);
        } else {
            noteService.update(note);
        }
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public ModelAndView showCreateNoteForm() {
        ModelAndView modelAndView = new ModelAndView("note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute("note") Note note) {
        note.setId(UUID.randomUUID());
        note.setCreatedAt(LocalDateTime.now());
//        note.setUser(userService.getCurrentUser());
        noteService.add(note);
        ModelAndView modelAndView = new ModelAndView("note/created");
        modelAndView.addObject("note", note);
        return modelAndView;
    }
}
