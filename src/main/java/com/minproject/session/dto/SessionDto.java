package com.minproject.session.dto;

import com.minproject.session.dao.Session;
import com.minproject.session.model.SessionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionDto {

    public String sessionId;

    public String userId;

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public SessionStatus sessionStatus;

    public static SessionDto from(Session s) {
        SessionDto dto = new SessionDto();
        dto.sessionId = s.getId().toString();
        dto.userId = s.getUserId();
        dto.sessionStatus = s.getSessionStatus();
        dto.startTime = s.getStartTime();
        return dto;
    }
}
