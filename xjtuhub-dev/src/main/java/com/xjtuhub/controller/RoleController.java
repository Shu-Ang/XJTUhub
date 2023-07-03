package com.xjtuhub.controller;

import com.xjtuhub.common.utils.DigestUtil;
import com.xjtuhub.common.utils.RedisOperator;
import com.xjtuhub.common.mail.MyMailSender;
import com.xjtuhub.common.result.ResponseStatusEnum;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Role;
import com.xjtuhub.service.impl.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "角色管理")
@RestController
public class RoleController {


}
