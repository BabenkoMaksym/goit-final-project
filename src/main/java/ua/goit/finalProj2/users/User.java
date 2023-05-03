package ua.goit.finalProj2.users;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.Entity;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", schema = "final_project")
public class User {
    @Id
    Long id;
    String username;
    String password;
    String email;
    String role;
    boolean enabled;

}
