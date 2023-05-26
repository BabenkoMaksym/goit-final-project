package ua.goit.finalProj2.messenger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.goit.finalProj2.messenger.chat.Chat;
import ua.goit.finalProj2.messenger.chat.ChatService;
import ua.goit.finalProj2.messenger.chat.ChatType;
import ua.goit.finalProj2.messenger.chat.ChatUsers;
import ua.goit.finalProj2.messenger.message.Message;
import ua.goit.finalProj2.messenger.message.MessageService;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;

import java.util.List;
import java.util.Map;

//localhost:9999/message/dialog


@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessengerController {

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    MessageService messageService;

    @GetMapping("/dialogs")
    public String showAllUserMessages(Model model, Authentication authentication) throws AuthenticationException {

        User user = userService.authorizeUser(authentication.isAuthenticated());

        List<Chat> userChats = chatService.getAllUserChats(authentication.getName());


        model.addAttribute("chats", userChats);
        return "/message/dialogs";
    }

    @PostMapping("/dialogs")
    public String chatWithUser(@RequestParam(name = "username") String userName,
                              RedirectAttributes redirectAttr,  Authentication authentication){
        try {
            Chat chat = chatService.findOrCreatePrivateChatByUserName(userName, authentication.getName());

            return String.format("redirect:/message/dialog?chat_id=%d", chat.getId());
        } catch (RuntimeException e){
            redirectAttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/message/dialogs";
        }
    }


    @GetMapping("/dialog")
    public String showCreateMessageForm(@RequestParam(name = "chat_id") Integer chat_id,
             Model model, Authentication authentication) throws AuthenticationException {


        return "/message/dialog";
    }

}
