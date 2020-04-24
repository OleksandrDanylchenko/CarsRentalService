package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.User;
import ua.alexd.CarRentService.service.UserService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        var users = userService.getAllUsers();
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        var foundedUser = userService.getUserById(id);
        return foundedUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User newUser) {
        return userService.addNewUser(newUser)
                ? new ResponseEntity<>(newUser, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User updUser) {
        return userService.updateUser(updUser)
                ? new ResponseEntity<>(updUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}