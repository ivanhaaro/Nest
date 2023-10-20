package com.nidito.nest.publication.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.nidito.nest.publication.domain.PublicationService;
import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.publication.domain.entity.dto.PublicationDto;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Publication entity")
@RequestMapping("/publications")
public class PublicationController {
    
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<Page<PublicationDto>> getPublications(Pageable pageable) {

        Page<PublicationDto> res = publicationService.getPublications(pageable)
                                                        .map(Publication::toDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    } 

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable UUID id) {

        PublicationDto res = publicationService.getPublicationById(id).toDto();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<PublicationDto> createPublication(@RequestBody @JsonView(Views.Create.class) PublicationDto publicationDto) {

        Publication res = publicationService.createPublication(publicationDto.toEntity(),publicationDto.getOwnerId());
        return new ResponseEntity<>(res.toDto(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<PublicationDto> updatePublication(@RequestBody @JsonView(Views.Create.class) PublicationDto publicationDto, @PathVariable UUID id) {

        Publication res = publicationService.updatePublication(publicationDto.toEntity(), id);
        return new ResponseEntity<>(res.toDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePublication(@PathVariable UUID id) {

        publicationService.deletePublication(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
