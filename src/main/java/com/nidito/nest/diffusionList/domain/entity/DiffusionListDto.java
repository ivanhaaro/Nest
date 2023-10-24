package com.nidito.nest.diffusionList.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;
import com.nidito.nest.user.domain.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiffusionListDto {
    
    @JsonView(Views.Retrieve.class)
    private UUID id;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private String name;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private UUID ownerId;

    @JsonView({Views.Retrieve.class, Views.Create.class, Views.Update.class})
    private List<UUID> friendsIds = new ArrayList<>();

    public DiffusionListDto(DiffusionList diffusionList) {

        this.id = diffusionList.getId();
        this.name = diffusionList.getName();
        this.ownerId = diffusionList.getOwner().getId();
        this.friendsIds = diffusionList.getFriends().stream().map(User::getId).toList();
    }
}

