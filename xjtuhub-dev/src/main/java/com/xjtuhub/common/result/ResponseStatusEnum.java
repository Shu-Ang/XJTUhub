package com.xjtuhub.common.result;

/**
 * 响应结果枚举，用于提供给GraceJSONResult返回给前端的
 * 本枚举类中包含了很多的不同的状态码供使用，可以自定义
 * 便于更优雅的对状态码进行管理，一目了然
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    // 50x
    ROLE_EXIST(501, false, "用户名已存在！"),
    LOG_UP_SUCCEED(502, true, "注册成功！"),
    UN_ACTIVATE(503, false, "账号未激活！请查收您的激活邮件，并点击邮件中的链接以完成激活。"),
    ACTIVATE_SUCCEED(504, true, "激活成功！"),
    ACTIVATE_FAIL(505, false, "激活失败！"),
    ROLE_NOT_EXIST(506, false, "用户名不存在！"),
    WRONG_PASSWORD(507, false, "密码错误！"),
    LOG_IN_SUCCEED(508, true, "登陆成功！");


    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }
}
