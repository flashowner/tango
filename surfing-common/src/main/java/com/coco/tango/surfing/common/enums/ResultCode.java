package com.coco.tango.surfing.common.enums;

/**
 * Http 统一返回 错误Code
 *
 * @author ckli01
 * @date 2018/6/25
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS("20000", "成功"),
    /**
     * 参数未通过验证
     */
    INVALID_PARAM("30000", "参数未通过验证"),
    /**
     * 参数类型有误
     */
    MISTYPE_PARAM("30001", "参数类型有误"),
    /**
     * 缺少参数
     */
    MISSING_PARAM("30002", "参数缺失"),
    /**
     * 用户访问异常
     */
    AUTH_ERROR("30003", "用户访问异常"),
    /**
     * 不支持的请求类型
     */
    UNSUPPORTED_METHOD("30003", "不支持的请求类型"),
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("50000", "系统异常"),
    /**
     * 业务异常
     */
    SERVICE_EXCEPTION("50001", "业务异常"),
    /**
     * dao exception
     */
    DAO_EXCEPTION("50002", "数据库操作异常"),
    /**
     * 用户异常
     */
    USER_EXCEPTION("50006", "用户异常"),
    ;


    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ResultCode e : ResultCode.values()) {
            if (e.getCode().equals(code)) {
                return e.getMessage();
            }
        }
        return null;
    }

    //是否包含
    public static boolean contains(String code) {
        for (ResultCode e : ResultCode.values()) {
            if (e.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }


    public static ResultCode getEnum(String code) {
        for (ResultCode e : ResultCode.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return this.message;
    }

}
