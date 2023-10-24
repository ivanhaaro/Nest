package com.nidito.nest.diffusionList.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidito.nest.diffusionList.domain.entity.DiffusionList;
import java.util.List;
import com.nidito.nest.user.domain.entity.User;


@Repository
public interface DiffusionListRepository extends JpaRepository<DiffusionList, UUID> {
    
    List<DiffusionList> findByOwner(User owner);
}
