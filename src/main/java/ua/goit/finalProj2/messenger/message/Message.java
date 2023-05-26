package ua.goit.finalProj2.messenger.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ua.goit.finalProj2.messenger.chat.Chat;
import ua.goit.finalProj2.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "message", schema = "final_project")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "message")
    String content;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    @ToString.Exclude
    Chat chat_id;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    @ToString.Exclude
    User user_id;

    boolean read;

    LocalDateTime createdAt;
}
