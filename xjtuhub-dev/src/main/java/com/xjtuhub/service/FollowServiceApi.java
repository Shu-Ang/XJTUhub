package com.xjtuhub.service;

import com.xjtuhub.entity.Follow;
import com.xjtuhub.entity.Role;

public interface FollowServiceApi {
    int countFansByRole(Role role);
    int follow(Follow follow);
    int isFollow(Follow follow);
    int unFollow(Follow follow);
}
