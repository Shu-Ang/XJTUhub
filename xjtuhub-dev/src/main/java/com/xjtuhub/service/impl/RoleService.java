package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;
import com.xjtuhub.mapper.RoleMapper;
import com.xjtuhub.service.RoleServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class RoleService implements RoleServiceApi {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int insertRoleSelective(Role role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public boolean isRoleExist(String roleId){
        return  (roleMapper.selectByPrimaryKey(roleId) != null);
    }

    @Override
    public Role findRoleById(String roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int updateRoleSelective(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(String roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<Role> findRoleList() {
        return roleMapper.selectRoleList();
    }

    @Override
    public List<Role> selectUserPage(Page page) {
        return roleMapper.selectRolePage(page);
    }

    @Override
    public int countRole() {
        return roleMapper.countRole();
    }


}
