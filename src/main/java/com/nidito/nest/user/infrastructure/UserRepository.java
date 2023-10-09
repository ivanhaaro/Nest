package com.nidito.nest.user.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.user.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
}
