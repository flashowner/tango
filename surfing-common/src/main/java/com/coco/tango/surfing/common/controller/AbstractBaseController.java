package com.coco.tango.surfing.common.controller;


import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.enums.ResultCode;


/**
 * 基础controller，用来包装http返回对象
 *
 * @author ckli01
 * @date 2018/6/25
 */
public abstract class AbstractBaseController {

    /**
     * 返回成功
     */
    public static <T> HttpRestResult<T> responseOK(ResultCode code, String message, T data) {
        HttpRestResult<T> restResult = new HttpRestResult<T>();
        restResult.setSuccess(true);
        restResult.setData(data);
        restResult.setCode(code.getCode());
        message = message == null ? code.getMessage() : message;
        restResult.setMessage(message);
        return restResult;
    }

    /**
     * 默认成功返回
     */
    public static <T> HttpRestResult<T> responseOK(T data) {
        return responseOK(ResultCode.SUCCESS, null, data);
    }

    /**
     * 返回成功，带信息
     */
    public static <T> HttpRestResult<T> responseOK(String message) {
        HttpRestResult<T> restResult = new HttpRestResult<T>();
        restResult.setSuccess(true);
        restResult.setData(null);
        restResult.setCode(ResultCode.SUCCESS.getCode());
        restResult.setMessage(message);
        return restResult;
    }

    /**
     * 返回成功，带信息
     */
    public static <T> HttpRestResult<T> responseOK(ResultCode code, String message) {
        HttpRestResult<T> restResult = new HttpRestResult<T>();
        restResult.setSuccess(true);
        restResult.setData(null);
        restResult.setCode(code.getCode());
        message = message == null ? code.getMessage() : message;
        restResult.setMessage(message);
        return restResult;
    }

    /**
     * 返回业务异常, 不带参数
     */
    public static <T> HttpRestResult<T> responseServiceException() {
        return responseOK(ResultCode.SERVICE_EXCEPTION, null, null);
    }

    /**
     * 返回系统异常，带code
     */
    public static <T> HttpRestResult<T> responseServiceException(ResultCode code) {
        return responseOK(code, null, null);
    }

    /**
     * 返回系统异常, 带信息
     */
    public static <T> HttpRestResult<T> responseServiceException(String message) {
        return responseOK(ResultCode.SERVICE_EXCEPTION, message, null);
    }

    /**
     * 返回系统异常，带code，带信息
     */
    public static <T> HttpRestResult<T> responseServiceException(ResultCode code, String message) {
        return responseOK(code, message, null);
    }

    /**
     * 返回系统异常, 不带参数
     */
    public static <T> HttpRestResult<T> responseSystemException() {
        return responseFail(ResultCode.SYSTEM_EXCEPTION, null, null);
    }

    /**
     * 返回系统异常，带code
     */
    public static <T> HttpRestResult<T> responseSystemException(ResultCode code) {
        return responseFail(code, null, null);
    }

    /**
     * 返回系统异常, 带信息
     */
    public static <T> HttpRestResult<T> responseSystemException(String message) {
        return responseFail(ResultCode.SYSTEM_EXCEPTION, null, message);
    }

    /**
     * 返回系统异常，带code，带信息
     */
    public static <T> HttpRestResult<T> responseSystemException(ResultCode code, String message) {
        return responseFail(code, null, message);
    }

    /**
     * 失败返回
     *
     * @param code    错误Code
     * @param data    数据内容，可为null
     * @param message 若为null，则使用Code对应的默认信息
     * @return
     */
    public static <T> HttpRestResult<T> responseFail(ResultCode code, T data, String message) {
        HttpRestResult<T> restResult = new HttpRestResult<T>();
        restResult.setSuccess(false);
        restResult.setData(data);
        restResult.setCode(code.getCode());
        message = message == null ? code.getMessage() : message;
        restResult.setMessage(message);
        return restResult;
    }

    /**
     * rest 请求返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public <T> HttpRestResult<T> httpRestResult(T data) {
        HttpRestResult<T> httpRestResult = new HttpRestResult<T>();
        httpRestResult.setData(data);
        httpRestResult.setSuccess(true);
        return httpRestResult;
    }

}
