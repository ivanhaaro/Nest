package com.nidito.nest.publication.application;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.publication.domain.PictureService;
import com.nidito.nest.publication.domain.entity.dto.NoteDto;
import com.nidito.nest.publication.domain.entity.dto.PictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.publication.domain.PublicationService;
import com.nidito.nest.publication.domain.entity.Publication;
import com.nidito.nest.publication.domain.entity.Publication.PublicationType;
import com.nidito.nest.publication.domain.entity.dto.PublicationDto;
import com.nidito.nest.shared.Views;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Publication entity")
@RequestMapping("/publications")
public class PublicationController {
    
    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PictureService pictureService;

    // @GetMapping
    // @JsonView(Views.Retrieve.class)
    // public ResponseEntity<Map<String, Object>> getPublications(@RequestParam(defaultValue = "0") int pageNum, 
    //                                                             @RequestParam(defaultValue = "10") int size, 
    //                                                             @RequestParam UUID userId) {

    //     List<PublicationDto> publications;
    //     Map<String, Object> response = new HashMap<>();
    //     Pageable pageable = PageRequest.of(pageNum, size);
    //     Page<Publication> page = publicationService.getPublications(pageable);
        
    //     publications = page.getContent().stream().map(Publication::toDto).toList();

    //     response.put("publications", publications);
    //     response.put("currentPage", page.getNumber());
    //     response.put("totalItems", page.getTotalElements());
    //     response.put("totalPages", page.getTotalPages());        

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // } 

    @GetMapping("/{userId}/feed")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<List<PublicationDto>> getFeed(@PathVariable UUID userId) {

        List<PublicationDto> res = publicationService.getFeed(userId).stream().map(Publication::toDto).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable UUID id) {

        PublicationDto res = publicationService.getPublicationById(id).toDto();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/note")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<PublicationDto> createNote(@RequestBody @JsonView(Views.Create.class) NoteDto publicationDto) {

        publicationDto.setPubliType(PublicationType.Note);
        Publication res = publicationService.createPublication(publicationDto.toEntity(),publicationDto.getOwnerId(), publicationDto.getWatchers());
        return new ResponseEntity<>(res.toDto(), HttpStatus.OK);
    }

    // @PutMapping("/{id}")
    // @JsonView(Views.Retrieve.class)
    // public ResponseEntity<PublicationDto> updatePublication(@RequestBody @JsonView(Views.Update.class) PublicationDto publicationDto, @PathVariable UUID id) {

    //     Publication res = publicationService.updatePublication(publicationDto.toEntity(), id);
    //     return new ResponseEntity<>(res.toDto(), HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Boolean> deletePublication(@PathVariable UUID id) {

    //     publicationService.deletePublication(id);
    //     return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    // }

    @PostMapping("/picture")
    @JsonView(Views.Retrieve.class)
    public ResponseEntity<String> createPicture(@RequestBody @JsonView(Views.Create.class) PictureDto pictureDto) {

        pictureDto.setPubliType(PublicationType.Picture);
        return new ResponseEntity<>(pictureService.createPicture(pictureDto), HttpStatus.OK);
    }


}
