package com.nidito.nest.diffusionList.domain;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.diffusionList.domain.entity.DiffusionList;

public interface DiffusionListService {
    
    List<DiffusionList> getDiffusionLists();
    List<DiffusionList> getDiffusionListsByOwnerId(UUID ownerId);
    DiffusionList getDiffusionListById(UUID id);
    DiffusionList createDiffusionList(DiffusionList diffusionList, UUID ownerId);
    DiffusionList updateDiffusionList(DiffusionList diffusionList, UUID id, UUID ownerId);    
    void deleteDiffusionList(UUID id);
}
