package ua.goit.finalProj2.messenger.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query(value = "SELECT chat.id, chat_name, chat_type FROM final_project.chat " +
            "INNER JOIN final_project.chat_users " +
            "ON chat.id = chat_users.chat_id " +
            "WHERE chat_users.username = :userName", nativeQuery = true)
    List<Chat> findChatsByUserName(@Param("userName") String userName);


}
