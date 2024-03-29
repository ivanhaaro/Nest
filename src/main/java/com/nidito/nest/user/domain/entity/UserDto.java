package com.nidito.nest.user.domain.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @JsonView(Views.Retrieve.class)
    private UUID id; 
    
    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String name;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String lastname;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String mail;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String username;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String password;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String state;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String avatar;

    public UserDto(User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.mail = user.getMail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.state = user.getState();
        this.avatar = user.getAvatar();
    }
}
