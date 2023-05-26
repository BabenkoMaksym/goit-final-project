package ua.goit.finalProj2.messenger.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.goit.finalProj2.users.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    //@Query("SELECT n FROM Message n WHERE n.recipient_user = :user")
    //List<Message> findByUser(User user);
}
