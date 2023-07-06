package com.xjtuhub.service;

import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;

import java.util.List;

public interface RoleServiceApi {
    public int insertRole(Role role);
    public int insertRoleSelective(Role role);
    public boolean isRoleExist(String roleId);
    public Role findRoleById(String roleId);
    public int updateRole(Role role);
    public int updateRoleSelective(Role role);
    public int deleteRole(String roleId);
    public List<Role> findRoleList();

    /**
     * 分页模糊查询
     */
    public List<Role> selectUserPage(Page page);

    //查询用户总数
    public int countRole();
}
