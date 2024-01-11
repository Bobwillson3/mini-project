package com.minproject.session.controller;

import com.minproject.session.model.ResponseStatus;
import com.minproject.session.request.LoginParam;
import com.minproject.session.request.RegisterParam;
import com.minproject.session.response.BaseResponse;
import com.minproject.session.response.LoginResponse;
import com.minproject.session.response.UserResponse;
import com.minproject.session.service.JwtUtil;
import com.minproject.session.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller    // This means that this class is a Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/rest/auth/login", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> login (@RequestBody LoginParam param) {
        ResponseStatus status = userService.login(param);
        LoginResponse response = new LoginResponse();
        if (status == ResponseStatus.SUCCESS) {
            String token = jwtUtil.createToken(userService.getUser(param.getUserName()));
            response.token = token;
        }
        response.status = status.getCode();
        response.message = status.getMessage();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/rest/auth/register", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> register (@RequestBody RegisterParam param) {
        return ResponseEntity.ok(userService.register(param));
    }
}
