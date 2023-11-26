package com.nidito.nest.capsule.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.capsule.domain.entity.Capsule;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, UUID> {
    
}
