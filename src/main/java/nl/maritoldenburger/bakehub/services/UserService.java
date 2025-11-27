package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.user.UserDto;
import nl.maritoldenburger.bakehub.dtos.user.UserInputDto;
import nl.maritoldenburger.bakehub.enums.Role;
import nl.maritoldenburger.bakehub.exceptions.AlreadyExistsException;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.UserMapper;
import nl.maritoldenburger.bakehub.models.Authority;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found"));

        return UserMapper.toDto(user);
    }

    public UserDto addUser(UserInputDto userInputDto) {

        if (userRepository.findByUsername(userInputDto.username).isPresent())
            throw new AlreadyExistsException("Username " + userInputDto.username + " already exists");

        if (userRepository.findByEmail(userInputDto.email).isPresent()) {
            throw new AlreadyExistsException("Email " + userInputDto.email + " already exists");
        }

        User user = UserMapper.toEntity(userInputDto);

        user.setPassword(passwordEncoder.encode(userInputDto.password));
        user.addAuthority(new Authority(userInputDto.username, Role.USER));

        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    public UserDto updateUser(Long id, UserInputDto userInputDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));

        user.setUsername(userInputDto.username);
        user.setEmail(userInputDto.email);
        if (userInputDto.password != null && !userInputDto.password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(userInputDto.password));
        }

        User updatedUser = userRepository.save(user);

        return UserMapper.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));
        userRepository.delete(user);
    }
}