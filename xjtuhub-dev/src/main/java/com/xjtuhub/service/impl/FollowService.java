package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Follow;
import com.xjtuhub.mapper.FollowMapper;
import com.xjtuhub.service.FollowServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService implements FollowServiceApi {
    @Autowired
    private FollowMapper followMapper;
    @Override
    public int countFans(String roleId) {
        return followMapper.countFans(roleId);
    }

    @Override
    public int follow(Follow follow) {
        return followMapper.insert(follow);
    }
}
