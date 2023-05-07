package ua.goit.finalProj2.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/edit")
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

    @GetMapping("/")
    public String feedNotes(@RequestParam(name = "page", required = false) Integer page, Model model){
        if(page==null)page=0;
        List<Note> notes = noteService.feedNote(page);
       model.addAttribute("notes", notes);
        return  "feed";
    }
}
