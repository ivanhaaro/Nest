package com.nidito.nest.publication.domain;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.publication.domain.entity.Publication;

public interface PublicationService {
    List<Publication> getPublications();
    Publication getPublicationById(UUID id);
    Publication createPublication(Publication publication, UUID ownerId);
    Publication updatePublication(Publication publication, UUID id);
    void deletePublication(UUID id);
}
