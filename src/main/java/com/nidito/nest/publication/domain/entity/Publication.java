package com.nidito.nest.publication.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "publication_table")
public class Publication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    private User owner;
    private Date date;

    public Publication(PublicationDto publicationDto) {

        this.id = publicationDto.getId();
    }

}
