package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;
import com.xjtuhub.service.impl.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RequestMapping("/admin/role")
@RestController
public class AdminRoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/roleList")
    @ApiOperation(value = "查询用户列表")
    public JSONResult showRoleList(){
        return JSONResult.ok(roleService.findRoleList());

    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/addRole")
    public JSONResult addRole(@RequestBody Role role){
        roleService.insertRole(role);
        return JSONResult.ok();
    }

    @ApiOperation(value = "查询用户总数")
    @GetMapping("/countRole")
    public JSONResult countRole(){
        return JSONResult.ok(roleService.countRole());
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/deleteRole")
    public JSONResult deleteRole(@RequestBody Role role){
        roleService.deleteRole(role.getRoleId());
        return JSONResult.ok("删除成功！");
    }
    @ApiOperation(value = "改变用户状态用户")
    @PostMapping("/changeRoleStatus")
    public JSONResult changeRoleStatus(@RequestBody Role role){
        Role record = roleService.findRoleById(role.getRoleId());
        record.setStatus(record.getStatus()==1?0:1);
        roleService.updateRoleSelective(record);
        return JSONResult.ok();
    }

    @GetMapping("/rolePage")
    @ApiOperation(value = "分页查询")
    public JSONResult showRolePage(Page page){
        // 获取总条数
        int totalNum = roleService.countRole();
        page.setTotalNum(totalNum);
        // 根据条件查询
        List<Role> roleList = roleService.selectUserPage(page);
        page.setResultList(roleList);
        return JSONResult.ok(page);
    }


}
