package com.minproject.session.dao;

import java.io.Serializable;

public class SessionRestaurantId implements Serializable {
    private String sessionId;

    private String userId;

    private String restaurantId;

    public SessionRestaurantId(String sessionId, String userId, String restaurantId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
