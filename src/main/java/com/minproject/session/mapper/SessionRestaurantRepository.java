package com.minproject.session.mapper;

import com.minproject.session.dao.SessionRestaurant;
import com.minproject.session.dao.SessionRestaurantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRestaurantRepository extends JpaRepository<SessionRestaurant, SessionRestaurantId> {
    List<SessionRestaurant> findAllBySessionId(String sessionId);

    List<SessionRestaurant> findAllByUserId(String userId);
}
