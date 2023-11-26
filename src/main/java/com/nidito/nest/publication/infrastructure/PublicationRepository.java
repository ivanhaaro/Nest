package com.nidito.nest.publication.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.publication.domain.entity.Publication;
import java.util.List;
import com.nidito.nest.capsule.domain.entity.Capsule;


@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    
    List<Publication> findByCapsule(Capsule capsule);
}
