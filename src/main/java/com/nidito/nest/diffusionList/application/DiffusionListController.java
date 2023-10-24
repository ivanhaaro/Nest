package com.nidito.nest.diffusionList.application;

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
import com.nidito.nest.diffusionList.domain.DiffusionListService;
import com.nidito.nest.diffusionList.domain.entity.DiffusionList;
import com.nidito.nest.diffusionList.domain.entity.DiffusionListDto;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/diffusionLists")
@Tag(name = "Diffusion list entity")
public class DiffusionListController {
    
    @Autowired
    private DiffusionListService service;

    @GetMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<DiffusionListDto>> getDiffusionLists() {

        List<DiffusionListDto> res = service.getDiffusionLists().stream()
                                                                .map(DiffusionListDto::new)
                                                                .toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<DiffusionListDto> getDiffusionListById(@PathVariable UUID id) {

        DiffusionListDto res = new DiffusionListDto(service.getDiffusionListById(id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<DiffusionListDto>> getDiffusionLists(@PathVariable UUID ownerId) {

        List<DiffusionListDto> res = service.getDiffusionListsByOwnerId(ownerId).stream()
                                                                                .map(DiffusionListDto::new)
                                                                                .toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<DiffusionListDto> createDiffusionList(@RequestBody @JsonView(Views.Create.class) DiffusionListDto diffusionListDto) {

        DiffusionListDto res = new DiffusionListDto(service.createDiffusionList(new DiffusionList(diffusionListDto), diffusionListDto.getOwnerId()));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<DiffusionListDto> createDiffusionList(@PathVariable UUID id, @RequestBody @JsonView(Views.Create.class) DiffusionListDto diffusionListDto) {

        DiffusionListDto res = new DiffusionListDto(service.updateDiffusionList(new DiffusionList(diffusionListDto), id, diffusionListDto.getOwnerId()));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDiffusionList(@PathVariable UUID id) {

        service.deleteDiffusionList(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
