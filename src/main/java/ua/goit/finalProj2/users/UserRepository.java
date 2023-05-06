package ua.goit.finalProj2.users;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.finalProj2.users.form_common.UserDAO;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO final_project.users(username, email, password) " +
            "VALUES(:#{#user.username}, :#{#user.email}, :#{#user.password})", nativeQuery = true)
    void create(@Param("user") UserDAO userDAO);

    @Query(value = "SELECT username, email, role, enabled FROM final_project.users " +
            "WHERE email = #{#user.email} AND password = #{#user.password}", nativeQuery = true)
    User getUserByEmail(@Param("user") UserDAO userDAO);
}
