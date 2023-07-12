package com.xjtuhub.mapper;

import com.xjtuhub.entity.Follow;
import com.xjtuhub.entity.Role;

import java.util.List;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer followId);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer followId);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);
    int countFansByRole(Role role);
    int countFollowsByRole(Role role);
    int isFollow(Follow follow);

    int unFollow(Follow follow);

}