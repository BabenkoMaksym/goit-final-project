package ua.goit.finalProj2.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.finalProj2.users.UserPrincipal;

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
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

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
        return "note/create";
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute("note") Note note, Principal principal) {
        String name = principal.getName();
        note.setId(UUID.randomUUID());
//        note.setUser(userService.getCurrentUser());
        noteService.add(note);
        ModelAndView modelAndView = new ModelAndView("note/created");
        modelAndView.addObject("note", note);
        return modelAndView;
    }
}
