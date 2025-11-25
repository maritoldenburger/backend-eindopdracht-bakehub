package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.user.UserDto;
import nl.maritoldenburger.bakehub.dtos.user.UserInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.UserMapper;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDto> dtoUsers = new ArrayList<>();

        for (User user : users) {
            dtoUsers.add(UserMapper.toDto(user));
        }
        return dtoUsers;
    }

    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));

        return UserMapper.toDto(user);
    }

    public UserDto addUser(UserInputDto userInputDto) {

        User user = UserMapper.toEntity(userInputDto);
        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    public UserDto updateUser(Long id, UserInputDto userInputDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));

        user.setUsername(userInputDto.username);
        user.setEmail(userInputDto.email);
        user.setPassword(userInputDto.password);

        User updatedUser = userRepository.save(user);

        return UserMapper.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));
        userRepository.delete(user);
    }
}
