package com.nidito.nest.publication.domain;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.nidito.nest.publication.domain.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {
    Page<Publication> getPublications(Pageable pageable);
    Publication getPublicationById(UUID id);
    Set<Publication> getFeed(UUID userId);
    Publication createPublication(Publication publication, UUID ownerId, List<UUID> usersIds);
    Publication updatePublication(Publication publication, UUID id);
    void deletePublication(UUID id);
}
