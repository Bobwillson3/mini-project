package com.minproject.session.dto;

import com.minproject.session.dao.Restaurant;

public class RestaurantDto {
    public String restaurantName;

    public static RestaurantDto from(Restaurant r) {
        RestaurantDto dto = new RestaurantDto();
        dto.restaurantName = r.getRestaurantName();
        return dto;
    }
}
