package com.nidito.nest.capsule.application;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.capsule.domain.CapsuleService;
import com.nidito.nest.capsule.domain.entity.Capsule;
import com.nidito.nest.capsule.domain.entity.CapsuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Capsule entity")
@RequestMapping("/capsule")
public class CapsuleController {
    
    @Autowired
    private CapsuleService capsuleService;

    @GetMapping("/user/{userId}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<CapsuleDTO>> getCapsules(@PathVariable UUID userId) {

        List<CapsuleDTO> res = capsuleService.getUserCapsules(userId).stream().map(Capsule::toDto).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<CapsuleDTO> getCapsuleById(@PathVariable UUID id) {

        CapsuleDTO res = capsuleService.getCapsuleById(id).toDto();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<CapsuleDTO> createCapsule(@RequestBody @JsonView(Views.Create.class) CapsuleDTO capsuleDTO, List<UUID> members)
    {
        Capsule res = capsuleService.createCapsule(capsuleDTO.toEntity(), members);
        return new ResponseEntity<>(res.toDto(), HttpStatus.OK);
    }

}

