package YahiaLakrikba.GestioneEventi.user;

import YahiaLakrikba.GestioneEventi.exeptions.BadRequestExeption;
import YahiaLakrikba.GestioneEventi.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public User findById(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteById(long id) throws NotFoundException {
        User found = findById(id);
        userRepository.delete(found);
    }

    public User updateUser(long id, User body) throws NotFoundException {
        User found = findById(id);
        found.setUsername(body.getUsername());
        found.setPassword(passwordEncoder.encode(body.getPassword()));
        return userRepository.save(found);
    }

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BadRequestExeption("Username " + user.getUsername() + " is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username " + username + " not found");
        }

        return user;

    }

}


