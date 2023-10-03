package com.nidito.nest.user.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
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
        this.name = userDto.getName();
        this.lastname = userDto.getLastname();
        this.mail = userDto.getMail();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
    }
}
