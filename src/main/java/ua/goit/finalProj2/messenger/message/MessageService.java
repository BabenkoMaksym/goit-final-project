package ua.goit.finalProj2.messenger.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;


    public List<Message> listMessages() {
        return messageRepository.findAll();
    }


    public List<Message> listOfMessagesByUser(User user) {
        return null;
    }

    private Stream<String> streamSendersUsernames(User user) {
        return null;
    }

    private Stream<String> streamRecipientsUsernames(User user) {
        return null;
    }





    public Map<String,List<Message>> getUserChats(User user, Set<String> userContacts) {
        Map<String, List<Message>> userChats = new LinkedHashMap<>();
        for (String contact : userContacts) {
            userChats.put(contact, dialogUserToUser
                            (user, userService.findByUsername(contact)));
        }
        return userChats;
    }


    public Message add(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> dialogUserToUser(User user, User companion) {
        List<Message> dialogUserToUser = new ArrayList<>(listMessages()
                .stream()
                .filter(message -> {return isBelongUser(user, message) &&
                    isBelongUser(companion, message);})
                .toList());

        dialogUserToUser.sort(Comparator
                .comparing(Message::getCreatedAt)
                .reversed());
        return dialogUserToUser;
    }

    private static boolean isBelongUser(User user, Message message) {
        return false;
    }
}
