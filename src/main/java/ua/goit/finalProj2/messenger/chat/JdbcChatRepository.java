package ua.goit.finalProj2.messenger.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcChatRepository {

    @Autowired
    private JdbcTemplate template;

    Optional<Chat> findPrivateChatByUserName(String userName, String senderName) throws RuntimeException {
        String query = "SELECT chat.id, chat.chat_name FROM final_project.chat LEFT JOIN " +
                        "final_project.chat_users ON chat_users.chat_id = chat.id " +
                        "WHERE chat_users.username = ? OR chat_users.username = ? " +
                        "AND chat.chat_type = 'PRIVATE' " +
                        "GROUP BY chat.id HAVING COUNT(chat.id) = 2 " +
                        "LIMIT 1;";

        try {
            return template.queryForObject(query,
                    new Object[]{userName, senderName},
                    (rs, rowNum) ->
                    Optional.of(Chat.builder()
                            .id(rs.getInt("id"))
                            .chatName("chat_name")
                            .build()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Помилка сервера, працюємо над вирішенням проблеми!");
        }
    }
}
