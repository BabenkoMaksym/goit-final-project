package ua.goit.finalProj2.messenger.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatUsersRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    void addUserToChatByUserName(Chat chat, String userName){
        jdbcTemplate.update("INSERT INTO final_project.chat_users VALUES(?, ?)", chat.getId(), userName);
    }

}
