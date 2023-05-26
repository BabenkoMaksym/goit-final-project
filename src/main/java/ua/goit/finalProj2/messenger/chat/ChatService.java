package ua.goit.finalProj2.messenger.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.finalProj2.users.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private JdbcChatUsersRepository jdbcChatUsersRepository;

    @Autowired
    private  JdbcChatRepository jdbcChatRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Chat> getAllUserChats(String userName){
        List<Chat> chats = chatRepository
                .findChatsByUserName(userName)
                .stream()
                .map(x ->
                        x.getChatType().equals(ChatType.PRIVATE) ?
                        x.toBuilder().chatName(x.getChatName().replaceAll(userName, "")).build() :
                        x)
                .toList();

        return chats;
    }

    public Chat findOrCreatePrivateChatByUserName(String userName, String senderName){
        return getPrivateChatByUserName(userName, senderName).or(() -> createPrivateChat(userName, senderName)).get();
        //return createPrivateChat(userName, senderName).get();
    }

    public Optional<Chat> getPrivateChatByUserName(String userName, String senderName){
        return jdbcChatRepository.findPrivateChatByUserName(userName, senderName);
    }

    @Transactional
    public Optional<Chat> createPrivateChat(String userName, String senderName) throws RuntimeException{
        if(userName.equals(senderName))
            throw new RuntimeException("Неможна писати самому собі!");
        else if(userRepository.findUserByUsername(userName).isEmpty())
            throw new RuntimeException("Користувач не існує!");


        Chat chat = new Chat();
        chat.setChatType(ChatType.PRIVATE);
        chat.setChatName(senderName + userName);
        chatRepository.save(chat);
        addUserToChatByUserName(chat, senderName);
        addUserToChatByUserName(chat, userName);
        return Optional.of(chat);
    }

    @Transactional
    public void addUserToChatByUserName(Chat chat, String userName){
        jdbcChatUsersRepository.addUserToChatByUserName(chat, userName);
    }
}
