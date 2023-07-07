package com.xjtuhub.service.impl;

import com.xjtuhub.mapper.LikesMapper;
import com.xjtuhub.service.LikesServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService implements LikesServiceApi {
    @Autowired
    private LikesMapper likesMapper;
    @Override
    public int countUserLikes(String roleId) {
        return likesMapper.countUserLikes(roleId);
    }
}
