package com.coco.tango.surfing.common.exception;

/**
 * 权限异常
 *
 * @author ckli01
 * @date 2018/10/18
 */
public class NoAuthException extends Exception {

    private static final long serialVersionUID = -6671424481361187143L;

    public NoAuthException(String message) {
        super(message);
    }

    public NoAuthException(Throwable cause) {
        super(cause);
    }

    public NoAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
