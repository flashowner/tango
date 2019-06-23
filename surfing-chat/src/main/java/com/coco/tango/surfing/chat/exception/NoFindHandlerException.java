package com.coco.tango.surfing.chat.exception;

/**
 * 找不到对应处理类
 *
 * @author ckli01
 * @date 2019-06-12
 */
public class NoFindHandlerException extends RuntimeException {


    private static final long serialVersionUID = 1322103436868144512L;

    public NoFindHandlerException(String message) {
        super(message);
    }

}
