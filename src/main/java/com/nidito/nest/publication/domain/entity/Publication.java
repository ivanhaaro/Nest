package com.nidito.nest.publication.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.nidito.nest.publication.domain.entity.dto.PublicationDto;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "publication_table")
public abstract class Publication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date date;
    private PublicationType publiType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    public enum PublicationType {
        Note, Picture, Song
    }

    public Publication(PublicationDto publicationDto) {

        this.id = publicationDto.getId();
        this.publiType = publicationDto.getPubliType();
    }

    public abstract PublicationDto toDto();
}
