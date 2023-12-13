package com.nidito.nest.letter.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidito.nest.letter.domain.entity.Letter;
import com.nidito.nest.letter.infrastructure.LetterRepository;
import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LetterService {

    @Autowired
    private UserService userService;

    @Autowired
    private LetterRepository letterRepository;

    public List<Letter> getLettersByUserId(UUID userId) {

        User receiver = userService.getUserById(userId);
        return letterRepository.findByReceiver(receiver);
    }

    public Letter getLetterById(UUID id) {

        Optional<Letter> letter = letterRepository.findById(id);
        if(letter.isEmpty()) throw new EntityNotFoundException("Letter not found with id " + id); 
        else return letter.get();
    }

    public Letter sendLetter(Letter letter, UUID originId, UUID receiverId) {

        User origin = userService.getUserById(originId);
        User receiver = userService.getUserById(receiverId);
        letter.setOrigin(origin);
        letter.setReceiver(receiver);
        return letterRepository.save(letter);
    }

    public Letter openLetter(UUID id) {

        Letter letter = this.getLetterById(id);
        letter.setOpened(true);
        return letterRepository.save(letter);
    }

    public Letter addFavouriteLetter(UUID id) {

        Letter letter = this.getLetterById(id);
        letter.setFavUser(letter.getReceiver());
        return letterRepository.save(letter);
    }

    public Letter deleteFavouriteLetter(UUID id) {

        Letter letter = this.getLetterById(id);
        letter.setFavUser(null);
        return letterRepository.save(letter);
    }
    
}
