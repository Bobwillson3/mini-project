package com.minproject.session.response;

import com.minproject.session.dto.SessionDto;

import java.util.List;

public class GetSessionListResponse extends BaseResponse {
    public List<SessionDto> sessions;
}
