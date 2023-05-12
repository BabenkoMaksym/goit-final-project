package ua.goit.finalProj2.messenger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
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

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    @ToString.Exclude
    User sender_user;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    @ToString.Exclude
    User recipient_user;

    boolean read;

    LocalDateTime createdAt;
}
