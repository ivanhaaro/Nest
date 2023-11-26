package com.nidito.nest.capsule.domain.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;
import com.nidito.nest.user.domain.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class CapsuleDTO {
    
    @JsonView(Views.Retrieve.class)
    private UUID id;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String title;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String description;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String imageURL;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private Date openDate;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private Set<User> members;

    public CapsuleDTO(Capsule capsule) {

        this.id = capsule.getId();
        this.title = capsule.getTitle();
        this.description = capsule.getDescription();
        this.openDate = capsule.getOpenDate();
        this.imageURL = capsule.getImageURL();
        this.members = capsule.getMembers();
    }

    public abstract Capsule toEntity();

}
