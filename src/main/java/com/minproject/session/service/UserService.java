package com.minproject.session.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minproject.session.dao.User;
import com.minproject.session.dto.UserDto;
import com.minproject.session.mapper.UserRepository;
import com.minproject.session.model.ResponseStatus;
import com.minproject.session.request.LoginParam;
import com.minproject.session.request.RegisterParam;
import com.minproject.session.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public ResponseStatus login(LoginParam param) {
        User user = userRepository.findByUserName(param.getUserName());
        if (user == null) {
            return ResponseStatus.USER_NONEXIST;
        } else {
            if (passwordEncoder.matches(param.getUserPassword(), user.getUserPassword())) {
                return ResponseStatus.SUCCESS;
            } else {
                return ResponseStatus.PASSWORD_NOT_MATCH;
            }
        }
    }

    public User getUser(String username) {
        User user = userRepository.findByUserName(username);
        return user;
    }

    public UserResponse register(RegisterParam param) {
        UserResponse response = new UserResponse();
        User user = userRepository.findByUserName(param.getUserName());
        if (user != null) {
            response.status = ResponseStatus.USERNAME_EXIST.getCode();
            response.message = ResponseStatus.USERNAME_EXIST.getMessage();
            response.user = UserDto.from(user);
            return response;
        } else {
            user = new User();
            user.setUserName(param.getUserName());
            user.setFirstName(param.getFirstName());
            user.setLastName(param.getLastName());
            // should do it in front end
            user.setUserPassword(passwordEncoder.encode(param.getUserPassword()));

            response.status = ResponseStatus.SUCCESS.getCode();
            response.message = ResponseStatus.SUCCESS.getMessage();
            response.user = UserDto.from(user);
            return response;
        }
    }
}
