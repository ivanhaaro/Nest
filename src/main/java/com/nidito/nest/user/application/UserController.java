package com.nidito.nest.user.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;
import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.domain.entity.UserDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User entity")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> res = userService.getUsers().stream()
                                    .map(UserDto::new)
                                    .collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {

        UserDto res = new UserDto(userService.getUserById(id));

        return new ResponseEntity<UserDto>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<UserDto>> getFriendsById(@PathVariable UUID id) {

        List<UserDto> res = userService.getFriends(id).stream()
                                                    .map(UserDto::new)
                                                    .toList();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> createUser(@RequestBody @JsonView(Views.Create.class) UserDto userDto) {

        UserDto res = new UserDto(userService.createUser(new User(userDto)));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/friends")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> addFriend(@PathVariable UUID id, @RequestParam UUID friendId) {

        UserDto res = new UserDto(userService.addFriend(id, friendId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody @JsonView(Views.Update.class) UserDto userDto) {

        UserDto res = new UserDto(userService.updateUser(new User(userDto), id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends")
    public ResponseEntity<UserDto> deleteFriend(@PathVariable UUID id, @RequestParam UUID friendId) {

        UserDto res = new UserDto(userService.deleteFriend(id, friendId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {

        UserDto res = new UserDto(userService.getUserByUsername(username));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/friends/request")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<String> sendFriendRequest(@PathVariable UUID id, @RequestParam UUID friendId) {

        String response = userService.sendFriendRequest(id, friendId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/request")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<String> deleteFriendRequest(@PathVariable UUID id, @RequestParam UUID friendId) {

        String response = userService.deleteFriendRequest(id, friendId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/requests")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<UserDto>> getFriendRequests(@PathVariable UUID id) {

        List<UserDto> pendingUserRequests = userService.getAllFriendRequests(id);
        return new ResponseEntity<>(pendingUserRequests, HttpStatus.OK);
    }
}
