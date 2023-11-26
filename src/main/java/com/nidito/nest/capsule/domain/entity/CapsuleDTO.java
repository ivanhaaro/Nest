package com.nidito.nest.publication.domain.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.publication.domain.entity.Publication.PublicationType;
import com.nidito.nest.shared.Views;

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


    public PublicationDto(Publication publication) {

        this.id = publication.getId();
        this.ownerId = publication.getOwner().getId();
        this.date = publication.getDate();
        this.publiType = publication.getPubliType();
    }

    public abstract Publication toEntity();

}
