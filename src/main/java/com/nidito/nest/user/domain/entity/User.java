package com.nidito.nest.user.domain.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_table", uniqueConstraints={@UniqueConstraint(columnNames={"username", "mail"})})
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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