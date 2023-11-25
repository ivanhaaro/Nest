package com.nidito.nest.publication.domain;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.publication.infrastructure.PublicationRepository;
import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicationService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PublicationRepository repository;

    public Page<Publication> getPublications(Pageable pageable) {
        
        return repository.findAll(pageable);
    }

    public Set<Publication> getFeed(UUID userId) {

        User user = userService.getUserById(userId);
        return user.getFeed();
    }

    public Publication getPublicationById(UUID id) {

        Optional<Publication> publication = repository.findById(id);
        if(publication.isEmpty()) throw new EntityNotFoundException("Publication with ID: " + id + " not found");
        else return publication.get();
    }

    public Publication createPublication(Publication publication, UUID ownerId, List<UUID> usersIds) {

        publication.setDate(Date.from(Instant.now()));
        publication.setOwner(userService.getUserById(ownerId));
        for(UUID id : usersIds) {
            userService
            .getUserById(id)
            .getFeed()
            .add(publication); 
        }
        return repository.save(publication);
    }

    public Publication updatePublication(Publication publication, UUID id) {

        Publication outdatedPublication = this.getPublicationById(id);

        publication.setOwner(outdatedPublication.getOwner());
        publication.setDate(outdatedPublication.getDate());
        publication.setId(id);
        return repository.save(publication);
    }

    public void deletePublication(UUID id) {

        if(!repository.existsById(id)) throw new EntityNotFoundException("Publication with ID: " + id + " not found");
        repository.deleteById(id);
    }

}
