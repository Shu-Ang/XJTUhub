package com.xjtuhub.controller;

import com.xjtuhub.common.exception.BusinessException;
import com.xjtuhub.common.mail.MyMailSender;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.common.result.ResponseStatusEnum;
import com.xjtuhub.common.utils.DigestUtil;
import com.xjtuhub.common.utils.RedisOperator;
import com.xjtuhub.common.utils.TokenUtils;
import com.xjtuhub.common.utils.XjtuhubConstants;
import com.xjtuhub.common.utils.CurrentUser;
import com.xjtuhub.entity.LoginUser;
import com.xjtuhub.entity.Role;
import com.xjtuhub.service.impl.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Api(tags = "注册、登录")
public class LoginController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private MyMailSender myMailSender;
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public JSONResult registerRole(@RequestBody Role role){
        if (roleService.isRoleExist(role.getRoleId()))
            return JSONResult.responseCustom(ResponseStatusEnum.ROLE_EXIST);
        role.setCreateDate(new Date());
        role.setStatus(0);
        role.setPassword(DigestUtil.hmacSign(role.getPassword()));
        myMailSender.SendMail(role);
        roleService.insertRole(role);
        return JSONResult.responseCustom(ResponseStatusEnum.LOG_UP_SUCCEED);
    }

    @PostMapping("/activate/{uuid}")
    @ApiOperation(value = "激活")
    public JSONResult activateRole(@PathVariable String uuid) {
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
    @ApiOperation(value = "登录")
    public JSONResult loginRole(@RequestBody LoginUser loginUser){
        String serverCode = redisOperator.get(loginUser.getVerificationKey());
        if(!loginUser.getVerificationCode().equals(serverCode)){
            return JSONResult.responseCustom(ResponseStatusEnum.CODE_ERROR);
        }
        if (!roleService.isRoleExist(loginUser.getUserId())) {
            return JSONResult.responseCustom(ResponseStatusEnum.ROLE_NOT_EXIST);
        }
        Role dbRole = roleService.findRoleById(loginUser.getUserId());
        if (dbRole.getStatus() == 0)
            return JSONResult.responseCustom(ResponseStatusEnum.UN_ACTIVATE);
        if (!dbRole.getPassword().equals(DigestUtil.hmacSign(loginUser.getUserPwd())))
            return JSONResult.responseCustom(ResponseStatusEnum.WRONG_PASSWORD);
        CurrentUser currentUser = new CurrentUser(loginUser.getUserId());
        String token = TokenUtils.loginSign(currentUser, dbRole.getPassword());
        return JSONResult.responseCustom(ResponseStatusEnum.LOG_IN_SUCCEED, token);
    }

    /**
     * 根据前端发送的token解析当前用户
     */
    @GetMapping("/curr-user")
    @ApiOperation(value = "根据前端发送的token解析当前用户")
    public JSONResult currUser(@RequestHeader(XjtuhubConstants.HEADER_TOKEN_NAME) String clientToken) {
        CurrentUser currentUser = TokenUtils.getCurrentUser(clientToken);
        return JSONResult.ok(currentUser);
    }

    // 校验令牌
    @GetMapping("/verifyToken")
    @ApiOperation(value = "校验Token")
    public JSONResult verifyToken(@RequestHeader(XjtuhubConstants.HEADER_TOKEN_NAME) String clientToken){
        try {
            TokenUtils.verify(clientToken);
            return JSONResult.ok();
        } catch (BusinessException e) {
            return JSONResult.responseCustom(ResponseStatusEnum.USER_UN_LOGIN);
        } catch (Exception e){
            return JSONResult.responseCustom(ResponseStatusEnum.SYS_ERROR);
        }
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "用户注销，删除Token")
    public JSONResult logout(@RequestHeader(XjtuhubConstants.HEADER_TOKEN_NAME) String clientToken) {
        // 从redis清除令牌
        TokenUtils.removeToken(clientToken);
        return JSONResult.ok();
    }

}
