package com.minproject.session.dto;

import com.minproject.session.dao.User;

import java.util.UUID;

public class UserDto {
    public UUID id;

    public String firstName;

    public String lastName;

    public String userName;

    public static UserDto from(User u) {
        UserDto dto = new UserDto();
        dto.id = u.getId();
        dto.firstName = u.getFirstName();
        dto.lastName = u.getLastName();
        dto.userName = u.getUserName();
        return dto;
    }
}
