package com.xjtuhub.mapper;

import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleList();

    List<Role> selectRolePage(Page page);

    int countRole();

    List<Role> selectFollowList(String roleId);

    List<Role> selectFanList(String roleId);
}