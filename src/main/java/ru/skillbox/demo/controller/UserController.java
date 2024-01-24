package ru.skillbox.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.demo.entity.Users;
import ru.skillbox.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody Users user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    @GetMapping(path = "/{id}")
    ResponseEntity<Users> getUser(@PathVariable long id) {
        final Users user = userService.getUser(id);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable long id) {
        final boolean updated = userService.updateUser(user, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable long id) {
        final boolean deleted = userService.deleteUser(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @GetMapping
    ResponseEntity<List<Users>> getUsers() {
        final List<Users> users = userService.getUsers();
        return users != null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/{sid}/{tid}")
    ResponseEntity<String> subscriptionUsers(@PathVariable Long sid, @PathVariable Long tid) {
        return new ResponseEntity<>(userService.subscriptionUsers(sid, tid), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{sid}/{tid}")
    ResponseEntity<String> deleteSubscriptionUsers(@PathVariable Long sid, @PathVariable Long tid) {
        return new ResponseEntity<>(userService.deleteSubscriptionUsers(sid, tid), HttpStatus.OK);
    }
}