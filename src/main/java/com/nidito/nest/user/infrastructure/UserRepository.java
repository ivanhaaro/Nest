package com.nidito.nest.user.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.user.domain.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserTemplate {
    
}
