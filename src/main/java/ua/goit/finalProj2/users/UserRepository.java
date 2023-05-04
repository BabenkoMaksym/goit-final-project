package ua.goit.finalProj2.users;

import jdk.jshell.EvalException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
