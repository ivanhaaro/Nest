package com.nidito.nest.diffusionList.domain.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "diffusionlist_table")
@NoArgsConstructor
public class DiffusionList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User owner;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<User> friends = new HashSet<>();

    public DiffusionList(DiffusionListDto diffusionListDto) {

        this.id = diffusionListDto.getId();
        this.name = diffusionListDto.getName();
    }

    
}
