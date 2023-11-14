package com.nidito.nest.user.infrastructure;

import com.nidito.nest.user.domain.entity.FriendRequest;
import com.nidito.nest.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {

    List<FriendRequest> findByOriginId(UUID originId);
    List<FriendRequest> findByReceiverId(UUID receiverId);

    void deleteByOriginAndReceiver(User origin, User receiver);
}