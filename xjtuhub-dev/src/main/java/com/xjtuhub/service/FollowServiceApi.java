package com.xjtuhub.service;

import com.xjtuhub.entity.Follow;

public interface FollowServiceApi {
    int countFans(String roleId);
    int follow(Follow follow);
}
