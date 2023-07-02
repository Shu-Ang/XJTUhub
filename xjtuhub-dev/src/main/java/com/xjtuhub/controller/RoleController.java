package com.xjtuhub.controller;

import com.xjtuhub.common.RedisOperator;
import com.xjtuhub.common.mail.MyMailSender;
import com.xjtuhub.common.result.ResponseStatusEnum;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Role;
import com.xjtuhub.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private MyMailSender myMailSender;
    @PostMapping("/register")
    public JSONResult registerRole(@RequestBody Role role){
        if (roleService.isRoleExist(role.getRoleId()))
            return JSONResult.responseCustom(ResponseStatusEnum.ROLE_EXIST);
        role.setCreateDate(new Date());
        role.setStatus(0);
        myMailSender.SendMail(role);
        roleService.insertRole(role);
        return JSONResult.responseCustom(ResponseStatusEnum.LOG_UP_SUCCEED);
    }

    @RequestMapping("/activate/{uuid}")
    public JSONResult changeUserStatus(@PathVariable String uuid) {
        if (redisOperator.keyIsExist(uuid)) {
            String roleId = redisOperator.get(uuid);
            //邮箱激活更新账号状态为激活，没有激活时就是未激活状态
            Role role = new Role();
            role.setRoleId(roleId);
            role.setStatus(1);
            roleService.updateRoleSelective(role);
            redisOperator.del(uuid);
            return JSONResult.responseCustom(ResponseStatusEnum.ACTIVATE_SUCCEED);
        } else {
            return JSONResult.responseCustom(ResponseStatusEnum.ACTIVATE_FAIL);
        }
    }


    @PostMapping("/login")
    public JSONResult loginRole(@RequestBody Role role){
        if (!roleService.isRoleExist(role.getRoleId()))
            return JSONResult.responseCustom(ResponseStatusEnum.ROLE_NOT_EXIST);
        Role dbRole = roleService.findRoleById(role.getRoleId());
        if (dbRole.getStatus() == 0)
            return JSONResult.responseCustom(ResponseStatusEnum.UN_ACTIVATE);
        if (!dbRole.getPassword().equals(role.getPassword()))
            return JSONResult.responseCustom(ResponseStatusEnum.WRONG_PASSWORD);
        return JSONResult.responseCustom(ResponseStatusEnum.LOG_IN_SUCCEED);
    }

}
