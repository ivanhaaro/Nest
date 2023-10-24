package com.nidito.nest.diffusionList.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidito.nest.diffusionList.domain.entity.DiffusionList;
import com.nidito.nest.diffusionList.infrastructure.DiffusionListRepository;
import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiffusionListServiceImpl implements DiffusionListService {

    @Autowired
    private DiffusionListRepository repository;

    @Autowired
    private UserService userService;

    public List<DiffusionList> getDiffusionLists() {
        
        return repository.findAll();
    }

    public List<DiffusionList> getDiffusionListsByOwnerId(UUID ownerId) {

        User owner = userService.getUserById(ownerId);
        return repository.findByOwner(owner);
    }

    public DiffusionList getDiffusionListById(UUID id) {

        Optional<DiffusionList> diffusionList = repository.findById(id);
        if(diffusionList.isEmpty()) throw new EntityNotFoundException("Diffusion list not found with id " + id);
        return diffusionList.get();
    }

    public DiffusionList createDiffusionList(DiffusionList diffusionList, UUID ownerId) {

        diffusionList.setOwner(userService.getUserById(ownerId));
        return repository.save(diffusionList);
    }

    public DiffusionList updateDiffusionList(DiffusionList diffusionList, UUID id, UUID ownerId) {

        if(!repository.existsById(id)) throw new EntityNotFoundException("Diffusion list not found with id " + id);
        diffusionList.setId(id);
        diffusionList.setOwner(userService.getUserById(ownerId));
        return repository.save(diffusionList);
    }

    public void deleteDiffusionList(UUID id) {

        if(!repository.existsById(id)) throw new EntityNotFoundException("Diffusion list not found with id " + id);
        repository.deleteById(id);
    }
}
