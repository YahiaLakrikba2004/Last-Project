package YahiaLakrikba.GestioneEventi.auth;

import YahiaLakrikba.GestioneEventi.exeptions.AuthenticationException;
import YahiaLakrikba.GestioneEventi.exeptions.BadRequestExeption;
import YahiaLakrikba.GestioneEventi.security.JwtResponse;
import YahiaLakrikba.GestioneEventi.security.JwtUtils;
import YahiaLakrikba.GestioneEventi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginRequest) {
        try {
            User user = authService.authenticateUser(loginRequest);
            String jwt = jwtUtils.generateToken((UserDetails) user);
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (AuthenticationException e) {
            //  restituisce una risposta con stato UNAUTHORIZED (401)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // restituisce una risposta con stato INTERNAL_SERVER_ERROR (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated User user, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestExeption("An unexpected error occurred");
        }
        try {

            User newUser = authService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
