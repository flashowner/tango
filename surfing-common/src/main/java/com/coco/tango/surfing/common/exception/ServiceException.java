package com.coco.tango.surfing.common.exception;


/**
 * Service层服务处理异常
 *
 * @author ckli01
 * @date 2018/10/18
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -4827639322754357173L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
