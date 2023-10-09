package com.nidito.nest.publication.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationDto {
    
    @JsonView(Views.Retrieve.class)
    private UUID id;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private UUID ownerId;

    @JsonView(Views.Retrieve.class)
    private Date date;

    public PublicationDto(Publication publication) {

        this.id = publication.getId();
        this.ownerId = publication.getOwner().getId();
        this.date = publication.getDate();
    }


}
