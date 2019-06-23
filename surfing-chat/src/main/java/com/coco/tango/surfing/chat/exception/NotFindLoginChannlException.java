package com.coco.tango.surfing.chat.exception;

/**
 * 未找到正常注册的连接异常
 *
 * @author ckli01
 * @date 2019-06-12
 */
public class NotFindLoginChannlException extends RuntimeException {


    private static final long serialVersionUID = 3587164036832179918L;

    public NotFindLoginChannlException(String message) {
        super(message);
    }
}
