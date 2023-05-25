package ua.goit.finalProj2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ua.goit.finalProj2.users.User;
import ua.goit.finalProj2.users.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            request.getSession().setAttribute("error", "The user "+ username + " does not exist. " +
                    "Go to the registration page.");
            throw new BadCredentialsException("Username not found");
        }

        if (!user.isEnabled()) {
            request.getSession().setAttribute("error", "User "+ username + " is blocked");
            throw new DisabledException("User is banned");

        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            request.getSession().setAttribute("error", "Invalid password");
            throw new BadCredentialsException("Invalid password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
