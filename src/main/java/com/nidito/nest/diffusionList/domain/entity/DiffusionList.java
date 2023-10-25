package com.nidito.nest.diffusionList.domain.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
@Table(name = "diffusionlist_table")
@NoArgsConstructor
public class DiffusionList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Set<UUID> friends = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "diffusionlist_id")
    private User owner;

    public DiffusionList(DiffusionListDto diffusionListDto) {

        this.id = diffusionListDto.getId();
        this.name = diffusionListDto.getName();
        this.friends = new HashSet<>(diffusionListDto.getFriendsIds());
    }

    
}
