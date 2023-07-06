package com.xjtuhub.common.result;

/**
 * 响应结果枚举，用于提供给SONResult返回给前端的
 * 本枚举类中包含了很多的不同的状态码供使用，可以自定义
 * 便于更优雅的对状态码进行管理，一目了然
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    // 50x
    ROLE_EXIST(501, false, "用户名已存在！"),
    LOG_UP_SUCCEED(502, true, "注册成功！我们会向您的邮箱发送激活邮件，请点击邮件中的链接以完成激活。"),
    UN_ACTIVATE(503, false, "账号未激活或者被冻结！"),
    ACTIVATE_SUCCEED(504, true, "激活成功！"),
    ACTIVATE_FAIL(505, false, "激活失败！"),
    ROLE_NOT_EXIST(506, false, "用户名不存在！"),
    WRONG_PASSWORD(507, false, "密码错误！"),
    LOG_IN_SUCCEED(508, true, "登陆成功！"),
    CODE_GENERATE_ERROR(509, false, "验证码生成失败！"),
    CODE_ERROR(510, false, "验证码错误！"),
    CODE_VALID(511, false, "验证码正确！"),
    USER_UN_LOGIN(512, false, "未登录！"),
    SYS_ERROR(513, false, "系统错误！"),
    INVALID_TOKEN(514, false, "令牌失效！");

    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String message;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.message = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String message() {
        return message;
    }
}
