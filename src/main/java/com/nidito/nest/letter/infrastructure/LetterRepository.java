package com.nidito.nest.letter.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.letter.domain.entity.Letter;
import java.util.List;
import com.nidito.nest.user.domain.entity.User;


@Repository
public interface LetterRepository extends JpaRepository<Letter, UUID> {
    
    List<Letter> findByReceiver(User receiver);
}
