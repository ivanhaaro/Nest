package com.nidito.nest.publication.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.publication.domain.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    
}
