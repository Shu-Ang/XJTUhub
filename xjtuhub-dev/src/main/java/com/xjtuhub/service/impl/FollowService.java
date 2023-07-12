package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Follow;
import com.xjtuhub.entity.Role;
import com.xjtuhub.mapper.FollowMapper;
import com.xjtuhub.service.FollowServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService implements FollowServiceApi {
    @Autowired
    private FollowMapper followMapper;
    @Override
    public int countFansByRole(Role role) {
        return followMapper.countFansByRole(role);
    }

    @Override
    public int countFollowsByRole(Role role) {
        return followMapper.countFollowsByRole(role);
    }

    @Override
    public int follow(Follow follow) {
        return followMapper.insert(follow);
    }

    @Override
    public int isFollow(Follow follow) {
        return followMapper.isFollow(follow);
    }

    @Override
    public int unFollow(Follow follow) {
        return followMapper.unFollow(follow);
    }
}
