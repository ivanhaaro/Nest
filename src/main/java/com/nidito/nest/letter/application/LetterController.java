package com.nidito.nest.letter.application;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.letter.domain.LetterService;
import com.nidito.nest.letter.domain.entity.Letter;
import com.nidito.nest.letter.domain.entity.LetterDto;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/letter")
@Tag(name = "Letter entity")
public class LetterController {

    @Autowired
    private LetterService letterService;

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<LetterDto> getLetterById(@PathVariable UUID id) {

        LetterDto res = new LetterDto(letterService.getLetterById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<LetterDto>> getLettersByUserId(@PathVariable UUID userId) {

        List<LetterDto> res = new ArrayList<>();
        
        for (Letter letter : letterService.getLettersByUserId(userId)) {
            if(letter.getDate().before(Date.from(Instant.now())))
                res.add(new LetterDto(letter));
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping()
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<LetterDto> createLetter(@RequestBody @JsonView(Views.Create.class) LetterDto letterDto) {

        LetterDto res = new LetterDto(letterService.sendLetter(new Letter(letterDto), letterDto.getOriginUserId(), letterDto.getReceiverUserId()));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/open/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<LetterDto> openLetter(@PathVariable UUID id) {

        LetterDto res = new LetterDto(letterService.openLetter(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/favourite/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<LetterDto> addToFavourite(@PathVariable UUID id) {

        LetterDto res = new LetterDto(letterService.addFavouriteLetter(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/favourite/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<LetterDto> deleteFromFavourite(@PathVariable UUID id) {

        LetterDto res = new LetterDto(letterService.deleteFavouriteLetter(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
