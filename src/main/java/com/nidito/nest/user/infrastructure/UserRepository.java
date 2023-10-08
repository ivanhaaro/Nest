package com.nidito.nest.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nidito.nest.user.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.username = ?1 AND u.id <> ?2")
    int countByUsernameAndDifferentId(String username, long id);
    
}
