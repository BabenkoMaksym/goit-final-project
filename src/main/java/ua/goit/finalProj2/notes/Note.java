package ua.goit.finalProj2.notes;

import lombok.Data;
import lombok.ToString;
import ua.goit.finalProj2.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "notes", schema = "final_project")
public class Note {

    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}