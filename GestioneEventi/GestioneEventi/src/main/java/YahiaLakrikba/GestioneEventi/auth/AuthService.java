package YahiaLakrikba.GestioneEventi.auth;


import YahiaLakrikba.GestioneEventi.exeptions.AuthenticationException;
import YahiaLakrikba.GestioneEventi.user.User;
import YahiaLakrikba.GestioneEventi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticateUser(UserLoginDTO loginDTO) throws AuthenticationException {
        User user = userService.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new AuthenticationException("Invalid email or password");
        }

        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new AuthenticationException("Invalid email or password");
        }
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }
}
