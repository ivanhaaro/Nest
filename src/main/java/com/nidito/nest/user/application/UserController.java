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
    private UserService service;

    @GetMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> res = service.getUsers().stream()
                                    .map(UserDto::new)
                                    .collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {

        UserDto res = new UserDto(service.getUserById(id));

        return new ResponseEntity<UserDto>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> createUser(@RequestBody @JsonView(Views.Create.class) UserDto userDto) {

        UserDto res = new UserDto(service.createUser(new User(userDto)));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody @JsonView(Views.Create.class) UserDto userDto) {

        UserDto res = new UserDto(service.updateUser(id, new User(userDto)));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {

        service.deleteUser(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);
    }



}
