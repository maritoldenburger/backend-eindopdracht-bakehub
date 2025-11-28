package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.user.UserDto;
import nl.maritoldenburger.bakehub.dtos.user.UserInputDto;
import nl.maritoldenburger.bakehub.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, Principal principal) {
        UserDto user = userService.getUserById(principal.getName(), id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserInputDto userInputDto) {
        UserDto savedUser = userService.addUser(userInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserInputDto userInputDto, @PathVariable Long id, Principal principal) {
        UserDto updatedUser = userService.updateUser(principal.getName(), id, userInputDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}