package ua.goit.finalProj2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserService;


@SpringBootApplication
public class FinalApplication {

	public static void main(String[] args) {

		SpringApplication.run(FinalApplication.class, args);
	}



}
