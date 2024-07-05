package YahiaLakrikba.GestioneEventi.user;


import YahiaLakrikba.GestioneEventi.exeptions.BadRequestExeption;
import YahiaLakrikba.GestioneEventi.exeptions.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    //Endpoint utenti impaginati
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy

    ){
        Page<User> users = userService.getUsers(page, size, orderBy);
        return ResponseEntity.ok(users);
    }

    //Endpoint creazione utente
    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (BadRequestExeption e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //Endpoint cercare utente con id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    //Endpoint aggiornamento utente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user){
        try {
            User updateUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updateUser);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Endpoint eliminazione utente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
