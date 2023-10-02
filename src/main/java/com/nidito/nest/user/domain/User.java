package com.nidito.nest.user.domain;


import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {
    
    @Id
    private String id;
    private String name;
    private String lastname;
    private String mail;
    private String username;
    private String password;


    public User(UserDto userDto) {

        this.id = userDto.getId();
        this.name = userDto.getLastname();
        this.lastname = userDto.getLastname();
        this.mail = userDto.getMail();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
    }
}
