package ua.goit.finalProj2.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.finalProj2.notes.form_common.NoteCreateException;
import ua.goit.finalProj2.users.UserPrincipal;
import ua.goit.finalProj2.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/edit")
    public ModelAndView showNoteForm(@RequestParam(name = "id", required = false) UUID id) {
        ModelAndView result = new ModelAndView("notes/edit");
        Note note = new Note();
        if (id != null) {
            note = noteService.getById(id);
        }
        result.addObject("note", note);
        return result;
    }

    @PostMapping("/edit")
    public String saveOrUpdateNote(@ModelAttribute("note") Note note, Model model)  {
        try {
            if (note.getId() == null) {
                noteService.add(note);
            } else {
                noteService.update(note);
            }
        } catch (NoteCreateException e) {
            model.addAttribute("error", e.getMessage());
            return "/notes/edit";
        }
        model.addAttribute("note", note);
        return "redirect:/notes/created";
    }
    @PostMapping("/delete")
    @ResponseBody
    public void deleteNote (@RequestParam("id") UUID id, HttpServletResponse resp){
        try{
            noteService.deleteById(id);
        } catch (IllegalArgumentException e){
            resp.setHeader("deleteError", e.getMessage());
        }
        try {
            resp.sendRedirect("http://localhost:9999/note/list");
            resp.setHeader("Location", "/note/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/")
    public String feedNotes(@RequestParam(name = "page", required = false) Integer page, Model model){
        page = page == null ? 0 : page >= 1 ? page - 1 : page;
        List<Note> notes = noteService.listPublicNotes(page);
        model.addAttribute("notes", notes);
        return  "feed";
    }

    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note, Model model,  Principal principal)  {
        String name = principal.getName();
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
}
