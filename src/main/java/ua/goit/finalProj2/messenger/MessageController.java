package ua.goit.finalProj2.messenger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserService;
import ua.goit.finalProj2.users.form_common.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;



@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    @Autowired
    UserRepository userRepository;
    MessageService messageService;
    @Autowired
    UserService userService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/dialogs")
    public String showAllUserMessages(Model model, Authentication authentication) throws AuthenticationException {

        User user = userService.authorizeUser(authentication.isAuthenticated());

        Map<String, List<Message>> userChats = messageService
                .getUserChats(user, messageService.userContacts(user));

        model.addAttribute("contacts", userChats);
        return "/message/dialogs";
    }


    @GetMapping("/dialog")
    public String showCreateMessageForm
            (@RequestParam(name = "user_name", required = false) String userName,
             Model model, Authentication authentication) throws AuthenticationException {

        List<Message> messageList = messageService.dialogUserToUser
                (userService.findByUsername
                                (authentication.getName()), userService.findByUsername(userName));

        model.addAttribute("messages", messageList);
        return "/message/dialog";
    }

    @PostMapping("/dialog")
    public String createMessage(@RequestParam(name = "user_name", required = false)String userName,
                                Message message, Model model, Authentication authentication) {
        String name = authentication.getName();

        message.setSender_user(userRepository.findUserByUsername(name).get());
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);
        message.setRecipient_user(userService.findByUsername(userName));
        messageService.add(message);

        model.addAttribute("message", message);
        return "/message/dialog";
    }
}
