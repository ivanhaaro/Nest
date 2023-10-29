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
public abstract class PublicationDto {
    
    @JsonView(Views.Retrieve.class)
    private UUID id;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private UUID ownerId;

    @JsonView(Views.Retrieve.class)
    private Date date;

    @JsonView(Views.Retrieve.class)
    private PublicationType publiType;

    @JsonView(Views.Create.class)
    private List<UUID> watchers = new ArrayList<>();

    public PublicationDto(Publication publication) {

        this.id = publication.getId();
        this.ownerId = publication.getOwner().getId();
        this.date = publication.getDate();
        this.publiType = publication.getPubliType();
    }

    public abstract Publication toEntity();

}
