package com.nidito.nest.publication.domain;

import java.util.UUID;

import com.nidito.nest.publication.domain.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {
    Page<Publication> getPublications(Pageable pageable);
    Publication getPublicationById(UUID id);
    Publication createPublication(Publication publication, UUID ownerId);
    Publication updatePublication(Publication publication, UUID id);
    void deletePublication(UUID id);
}
