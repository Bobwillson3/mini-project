package com.minproject.session.mapper;

import com.minproject.session.dao.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Restaurant findByRestaurantName(String restaurantName);
}
