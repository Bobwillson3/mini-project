package com.minproject.session.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minproject.session.dao.Restaurant;
import com.minproject.session.dao.Session;
import com.minproject.session.dao.SessionRestaurant;
import com.minproject.session.dao.User;
import com.minproject.session.dto.RestaurantDto;
import com.minproject.session.dto.SessionDto;
import com.minproject.session.mapper.RestaurantRepository;
import com.minproject.session.mapper.SessionRepository;
import com.minproject.session.mapper.SessionRestaurantRepository;
import com.minproject.session.mapper.UserRepository;
import com.minproject.session.model.ResponseStatus;
import com.minproject.session.model.SessionStatus;
import com.minproject.session.request.JoinSessionParam;
import com.minproject.session.response.UserRestaurantDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SessionRestaurantRepository sessionRestaurantRepository;

    public SessionDto createSession(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (user == null) {
            return null;
        } else {
            Session session = new Session();
            session.setUserId(userId);
            session.setStartTime(LocalDateTime.now());
            session.setSessionStatus(SessionStatus.OPEN);
            session = sessionRepository.save(session);
            //TODO: add a notification function in the future, currently no time
            return SessionDto.from(session);
        }
    }

    public ResponseStatus joinSession(JoinSessionParam param) {
        User user = userRepository.findById(UUID.fromString(param.getUserId())).orElse(null);
        if (user == null) {
            return ResponseStatus.USER_NONEXIST;
        } else {
            Session session = sessionRepository.findById(UUID.fromString(param.getSessionId())).orElse(null);
            if (session == null) {
                return ResponseStatus.SESSION_NONEXIST;
            }
            if (session.getSessionStatus().equals(SessionStatus.END)) {
                return ResponseStatus.SESSION_END;
            }

            Restaurant restaurant = restaurantRepository.findByRestaurantName(param.getRestaurantName());
            if (restaurant == null) {
                restaurant = new Restaurant();
                restaurant.setRestaurantName(param.getRestaurantName());
                restaurant = restaurantRepository.save(restaurant);
            }
            SessionRestaurant sessionRestaurant = new SessionRestaurant();
            sessionRestaurant.setSessionId(param.getSessionId());
            sessionRestaurant.setRestaurantId(restaurant.getId().toString());
            sessionRestaurant.setUserId(param.getUserId());
            sessionRestaurantRepository.save(sessionRestaurant);
            return ResponseStatus.SUCCESS;
        }
    }

    public String endSession(String sessionId) {
        Session session = sessionRepository.findById(UUID.fromString(sessionId)).orElse(null);
        if (session == null) {
            return null;
        }
        List<SessionRestaurant> sessionRestaurants = sessionRestaurantRepository.findAllBySessionId(sessionId);
        List<String> restaurantIds = sessionRestaurants.stream().map(SessionRestaurant::getRestaurantId).toList();
        int rnd = new Random().nextInt(restaurantIds.size());
        String restaurantId = restaurantIds.get(rnd);

        session.setEndTime(LocalDateTime.now());
        session.setSessionStatus(SessionStatus.END);
        session.setRestaurantId(restaurantId);

        Restaurant restaurant = restaurantRepository.findById(UUID.fromString(restaurantId)).orElse(null);

        if (restaurant != null) {
            sessionRepository.save(session);
            return restaurant.getRestaurantName();
        } else {
            throw new RuntimeException("restaurant not exist");
        }
    }

    public List<UserRestaurantDto> getSession(String sessionId) {
        List<SessionRestaurant> sessionRestaurants = sessionRestaurantRepository.findAllBySessionId(sessionId);
        List<UserRestaurantDto> userRestaurantDtos = new ArrayList<>();
        List<User> users = userRepository.findAllById(sessionRestaurants.stream().map(x ->
            UUID.fromString(x.getUserId())).collect(Collectors.toList()));
        List<Restaurant> restaurants = restaurantRepository.findAllById(sessionRestaurants.stream().map(x ->
                UUID.fromString(x.getRestaurantId())).collect(Collectors.toList()));
        Map<UUID, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<UUID, Restaurant> restaurantMap = restaurants.stream().collect(Collectors.toMap(Restaurant::getId, Function.identity()));
        for (SessionRestaurant s: sessionRestaurants) {
            UserRestaurantDto dto = new UserRestaurantDto();
            dto.firstName = userMap.get(UUID.fromString(s.getUserId())).getFirstName();
            dto.lastName = userMap.get(UUID.fromString(s.getUserId())).getLastName();
            dto.restaurantName = restaurantMap.get(UUID.fromString(s.getRestaurantId())).getRestaurantName();
            userRestaurantDtos.add(dto);
        }
        return userRestaurantDtos;
    }

    public List<SessionDto> getSessions() {
        List<Session> sessions = sessionRepository.findAllBySessionStatusAndStartDate(SessionStatus.OPEN, LocalDate.now());
        List<SessionDto> dtos = new ArrayList<>();
        for (Session s: sessions) {
            dtos.add(SessionDto.from(s));
        }
        return dtos;

    }

    public ResponseStatus addRestaurant(String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurant != null) {
            return ResponseStatus.RESTAURANT_EXIST;
        } else {
            restaurant = new Restaurant();
            restaurant.setRestaurantName(restaurantName);
            restaurantRepository.save(restaurant);
            return ResponseStatus.SUCCESS;
        }
    }

    public List<RestaurantDto> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(RestaurantDto::from).collect(Collectors.toList());
    }
}
