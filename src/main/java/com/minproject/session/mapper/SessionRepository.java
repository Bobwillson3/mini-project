package com.minproject.session.mapper;

import com.minproject.session.dao.Session;
import com.minproject.session.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findAllBySessionStatusAndStartDate(SessionStatus status, LocalDate startDate);
}
