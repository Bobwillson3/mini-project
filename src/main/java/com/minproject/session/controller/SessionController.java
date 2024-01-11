package com.minproject.session.controller;

import com.minproject.session.dto.RestaurantDto;
import com.minproject.session.dto.SessionDto;
import com.minproject.session.model.ResponseStatus;
import com.minproject.session.request.*;
import com.minproject.session.response.*;
import com.minproject.session.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller	// This means that this class is a Controller
public class SessionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/createSession", method = RequestMethod.POST)
	public ResponseEntity<CreateSessionResponse> createSession (@RequestBody CreateSessionParam param) {
		CreateSessionResponse response = new CreateSessionResponse();
		SessionDto session = sessionService.createSession(param.getUserId());
		if (session == null) {
			response.status = ResponseStatus.USER_NONEXIST.getCode();
			response.message = ResponseStatus.USER_NONEXIST.getMessage();
		} else {
			response.status = ResponseStatus.SUCCESS.getCode();
			response.message = ResponseStatus.SUCCESS.getMessage();
			response.session = session;
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/endSession", method = RequestMethod.POST)
	public ResponseEntity<EndSessionResponse> endSession (@RequestBody EndSessionParam param) {
		String restaurantName = sessionService.endSession(param.getSessionId());
		EndSessionResponse response = new EndSessionResponse();
		if (restaurantName == null) {
			response.status = ResponseStatus.SESSION_NONEXIST.getCode();
			response.message = ResponseStatus.SESSION_NONEXIST.getMessage();
		} else {
			response.status = ResponseStatus.SUCCESS.getCode();
			response.message = ResponseStatus.SUCCESS.getMessage();
			response.restaurantName = restaurantName;
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/joinSession", method = RequestMethod.POST)
	public ResponseEntity<JoinSessionResponse> joinSession (@RequestBody JoinSessionParam param) {
		ResponseStatus status = sessionService.joinSession(param);
		JoinSessionResponse response = new JoinSessionResponse();
		response.status = status.getCode();
		response.message = status.getMessage();
		return ResponseEntity.ok(response);
	}


	@RequestMapping(value = "/getSession", method = RequestMethod.GET)
	public ResponseEntity<GetSessionResponse> getSession (@RequestBody GetSessionParam param) {
		GetSessionResponse response = new GetSessionResponse();
		response.status = ResponseStatus.SUCCESS.getCode();
		response.message = ResponseStatus.SUCCESS.getMessage();
		response.userChoice = sessionService.getSession(param.getSessionId());
		return ResponseEntity.ok(response);
	}


	@RequestMapping(value = "/getSessions", method = RequestMethod.GET)
	public ResponseEntity<GetSessionListResponse> getSession () {
		GetSessionListResponse response = new GetSessionListResponse();
		List<SessionDto> sessions = sessionService.getSessions();
		if (sessions == null) {
			response.status = ResponseStatus.USER_NONEXIST.getCode();
			response.message = ResponseStatus.USER_NONEXIST.getMessage();
		} else {
			response.status = ResponseStatus.SUCCESS.getCode();
			response.message = ResponseStatus.SUCCESS.getMessage();
			response.sessions = sessions;
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/addRestaurant", method = RequestMethod.POST)
	public ResponseEntity<AddRestaruantResponse> addRestaurant (@RequestBody AddRestaruantParam param) {
		ResponseStatus status = sessionService.addRestaurant(param.getRestaurantname());
		AddRestaruantResponse response = new AddRestaruantResponse();
		response.status = status.getCode();
		response.message = status.getMessage();
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/getRestaurants", method = RequestMethod.GET)
	public ResponseEntity<RestaruantsResponse> getRestaurants () {
		List<RestaurantDto> restaurants = sessionService.getRestaurants();
		RestaruantsResponse response = new RestaruantsResponse();
		response.status = ResponseStatus.SUCCESS.getCode();
		response.message = ResponseStatus.SUCCESS.getMessage();
		response.restaurants = restaurants;
		return ResponseEntity.ok(response);
	}
}
