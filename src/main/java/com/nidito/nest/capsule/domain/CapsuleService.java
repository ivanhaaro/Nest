package com.nidito.nest.capsule.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nidito.nest.capsule.domain.entity.Capsule;
import com.nidito.nest.capsule.infrastructure.CapsuleRepository;
import com.nidito.nest.publication.domain.PublicationService;
import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CapsuleService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CapsuleRepository repository;

    @Autowired
    private PublicationService publicationService;

    public Page<Capsule> getCapsules(Pageable pageable) {
        
        return repository.findAll(pageable);
    }

    public Capsule getCapsuleById(UUID id) {

        Optional<Capsule> capsule = repository.findById(id);
        if(capsule.isEmpty()) throw new EntityNotFoundException("Capsule with ID: " + id + " not found");
        else return capsule.get();
    }

    public Set<Capsule> getUserCapsules(UUID userId)
    {
        User user = userService.getUserById(userId);
        return user.getCapsules();
    }

    public Capsule createCapsule(Capsule capsule, List<UUID> usersIds) {

        for(UUID id : usersIds) 
        {
            User u = userService.getUserById(id);
            u.getCapsules().add(capsule);
            capsule.getMembers().add(u);
        }
        return repository.save(capsule);
    }

    public Capsule addPublication(UUID capsuleId, UUID publicationId) {

        Capsule capsule = this.getCapsuleById(capsuleId);
        Publication publication = publicationService.getPublicationById(publicationId);

        publication.setCapsule(capsule);
        return repository.save(capsule);
    }


    public Capsule updateCapsule(Capsule capsule, UUID id) {

        Capsule outdatedCapsule = this.getCapsuleById(id);

        capsule.setMembers(outdatedCapsule.getMembers());
        capsule.setOpenDate(outdatedCapsule.getOpenDate());
        capsule.setId(id);
        return repository.save(capsule);
    }

    public void deleteCapsule(UUID id) {

        if(!repository.existsById(id)) throw new EntityNotFoundException("Capsule with ID: " + id + " not found");
        repository.deleteById(id);
    }

}
