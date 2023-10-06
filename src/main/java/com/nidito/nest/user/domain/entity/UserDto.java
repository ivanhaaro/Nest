package com.nidito.nest.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @JsonView(Views.Retrieve.class)
    private int id; 
    
    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String name;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String lastname;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String mail;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String username;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String password;

    public UserDto(User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.mail = user.getMail();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
