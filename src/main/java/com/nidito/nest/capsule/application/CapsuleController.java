package com.nidito.nest.capsule.application;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.capsule.domain.CapsuleService;
import com.nidito.nest.capsule.domain.entity.Capsule;
import com.nidito.nest.capsule.domain.entity.CapsuleDto;
import com.nidito.nest.publication.domain.PublicationService;
import com.nidito.nest.publication.domain.entity.dto.PublicationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Capsule entity")
@RequestMapping("/capsules")
public class CapsuleController {
    
    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/user/{userId}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<CapsuleDto>> getCapsules(@PathVariable UUID userId) {

        List<CapsuleDto> res = capsuleService.getUserCapsules(userId).stream().map(CapsuleDto::new).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<CapsuleDto> getCapsuleById(@PathVariable UUID id) {

        CapsuleDto res = new CapsuleDto(capsuleService.getCapsuleById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}/publications")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<PublicationDto>> getPublicationsFromCapsule(@PathVariable UUID id) {
        
        Capsule capsule = capsuleService.getCapsuleById(id);
        List<PublicationDto> res = publicationService.getPublicationByCapsule(capsule).stream().map(p -> p.toDto()).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<CapsuleDto> createCapsule(@RequestBody @JsonView(Views.Create.class) CapsuleDto capsuleDTO) {

        Capsule res = capsuleService.createCapsule(new Capsule(capsuleDTO), capsuleDTO.getMembers());
        return new ResponseEntity<>(new CapsuleDto(res), HttpStatus.OK);
    }

    @PostMapping("/{id}/publications")
    @JsonView(Views.Create.class)
    public ResponseEntity<CapsuleDto> addPublication(@PathVariable UUID id, @RequestParam UUID publicationId) {

        CapsuleDto res = new CapsuleDto(capsuleService.addPublication(id, publicationId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

