package com.xjtuhub.common.result;

import java.util.Map;

/**
 * 自定义响应数据类型枚举升级版本
 *
 * @Title: JSONResult.java
 * @Package com.xjtuhub.result
 * @Description: 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 */
public class JSONResult {

    // 响应业务状态码
    private Integer status;

    // 响应消息
    private String message;

    // 是否成功
    private Boolean success;

    // 响应数据，可以是Object，也可以是List或Map等
    private Object data;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     * @param data
     * @return
     */

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }
    /**
     * 成功返回，带有数据的，带有消息的，直接往OK方法丢data数据和消息即可
     * @param message, data
     * @return
     */
    public static JSONResult ok(String message, Object data) {
        return new JSONResult(message, data);
    }
    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     * @return
     */
    public static JSONResult ok() {
        return new JSONResult(com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS);
    }

    public static JSONResult ok(String message) {
        return new JSONResult(message);
    }

    public JSONResult(Object data) {
        this.status = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.status();
        this.message = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.message();
        this.success = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }

    public JSONResult(String msg) {
        this.status = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.status();
        this.message = msg;
        this.success = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.success();
        this.data = null;
    }

    public JSONResult(String message, Object data) {
        this.status = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.status();
        this.message = message;
        this.success = com.xjtuhub.common.result.ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }

    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     * @return
     */
    public static JSONResult error() {
        return new JSONResult(com.xjtuhub.common.result.ResponseStatusEnum.FAILED);
    }

    public static JSONResult error(String message) {
        return new JSONResult(message, com.xjtuhub.common.result.ResponseStatusEnum.FAILED);
    }


    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     * @param map
     * @return
     */
    public static JSONResult errorMap(Map map) {
        return new JSONResult(com.xjtuhub.common.result.ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     * @param msg
     * @return
     */
    public static JSONResult errorMsg(String msg) {
        return new JSONResult(com.xjtuhub.common.result.ResponseStatusEnum.FAILED, msg);
    }


    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus
     * @return
     */

    public static JSONResult responseCustom(com.xjtuhub.common.result.ResponseStatusEnum responseStatus) {
        return new JSONResult(responseStatus);
    }

    public static JSONResult responseCustom(com.xjtuhub.common.result.ResponseStatusEnum responseStatus, Object data) {
        return new JSONResult(responseStatus, data);
    }

    public JSONResult(com.xjtuhub.common.result.ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.message = responseStatus.message();
        this.success = responseStatus.success();
    }
    public JSONResult(com.xjtuhub.common.result.ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.message = responseStatus.message();
        this.success = responseStatus.success();
        this.data = data;
    }
    public JSONResult(com.xjtuhub.common.result.ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.message = msg;
        this.success = responseStatus.success();
    }

    public JSONResult() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
