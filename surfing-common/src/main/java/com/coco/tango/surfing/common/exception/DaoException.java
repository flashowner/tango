package com.coco.tango.surfing.common.exception;

/**
 * DAO层服务处理异常
 *
 * @author ckli01
 * @date 2018/10/18
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = -3300345965819780564L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
