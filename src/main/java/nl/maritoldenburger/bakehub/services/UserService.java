package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));

        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        user.setPassword(updated.getPassword());
        user.setRole(updated.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));
        userRepository.delete(user);
    }
}
