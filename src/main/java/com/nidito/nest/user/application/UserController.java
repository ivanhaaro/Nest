package com.nidito.nest.user.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidito.nest.user.domain.UserDto;
import com.nidito.nest.user.domain.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> res = service.getUsers().stream()
                                    .map(UserDto::new)
                                    .collect(Collectors.toList());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
