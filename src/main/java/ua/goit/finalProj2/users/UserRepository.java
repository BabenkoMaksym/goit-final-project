package ua.goit.finalProj2.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.finalProj2.users.form_common.UserDto;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO final_project.users(username, password, email, role, enabled) " +
            "VALUES(:#{#user.username}, :#{#user.password}, :#{#user.email}, :#{#user.role}), :#{#user.enabled}", nativeQuery = true)
    void create(@Param("user") User user);

    @Query(value = "SELECT username, email, role, enabled FROM final_project.users " +
            "WHERE username = #{#user.username} AND password = #{#user.password}", nativeQuery = true)
    User getUserByUsername(@Param("user") UserDto userDto);


    @Query(value = "SELECT * FROM final_project.users WHERE username = :username", nativeQuery = true)
    public Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT * FROM final_project.users WHERE email = :email", nativeQuery = true)
    public Optional<User> findUserByEmail(String email);
}
