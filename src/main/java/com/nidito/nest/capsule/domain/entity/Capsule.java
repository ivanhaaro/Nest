package com.nidito.nest.capsule.domain.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.publication.domain.entity.dto.PublicationDto;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "capsule_table")
public abstract class Capsule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private Date openDate;
    private String imageURL;
    @ManyToMany(mappedBy = "capsules")
    private Set<User> members = new HashSet<>(); 
    @OneToMany
    private List<Publication> publications;


    public Capsule(CapsuleDTO capsuleDTO) {

        this.id = capsuleDTO.getId();
        this.openDate = capsuleDTO.getOpenDate();
        this.title = capsuleDTO.getTitle();
        this.description = capsuleDTO.getDescription();
        this.imageURL = capsuleDTO.getImageURL();
        this.members = capsuleDTO.getMembers();
    }

    public abstract CapsuleDTO toDto();
}
