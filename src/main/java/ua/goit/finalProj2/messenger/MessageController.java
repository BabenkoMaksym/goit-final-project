package ua.goit.finalProj2.messenger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.finalProj2.users.UserRepository;
import ua.goit.finalProj2.users.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

}
