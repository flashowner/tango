package com.coco.tango.surfing.common.controller.exception;

import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.controller.AbstractBaseController;
import com.coco.tango.surfing.common.enums.ResultCode;
import com.coco.tango.surfing.common.exception.DaoException;
import com.coco.tango.surfing.common.exception.NoAuthException;
import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends AbstractBaseController {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    /**
     * 异常日志记录
     */
    private void logErrorRequest(Exception e) {
        String info = String.format("报错API URL: %s%nQuery String: %s",
                httpServletRequest.getRequestURI(),
                httpServletRequest.getQueryString());
        String ipInfo = "报错访问者IP信息：" + IpUtils.getIpAddress(httpServletRequest);
        log.error(info);
        log.error(ipInfo);
        log.error(e.getMessage(), e);
    }

    /**
     * 参数未通过@Valid验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected HttpRestResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        logErrorRequest(exception);
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String error = fieldError.getDefaultMessage();
            return responseFail(ResultCode.INVALID_PARAM, null, error);
        }
        return responseSystemException(ResultCode.INVALID_PARAM);
    }

    /**
     * 参数格式有误
     */
    @ExceptionHandler({TypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    protected HttpRestResult typeMismatchExceptionHandler(Exception exception) {
        logErrorRequest(exception);
        return responseSystemException(ResultCode.MISTYPE_PARAM);
    }

    /**
     * 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    protected HttpRestResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        logErrorRequest(exception);
        return responseSystemException(ResultCode.MISSING_PARAM);
    }

    /**
     * 不支持的请求类型
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    protected HttpRestResult httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        logErrorRequest(exception);
        return responseSystemException(ResultCode.UNSUPPORTED_METHOD);
    }

    /**
     * NoAuth层服务异常
     */
    @ExceptionHandler(NoAuthException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected HttpRestResult noAuthExceptionHandler(NoAuthException exception) {
        logErrorRequest(exception);
        return responseSystemException(ResultCode.USER_EXCEPTION);
    }

    /**
     * DAO层服务异常
     */
    @ExceptionHandler(DaoException.class)
    @ResponseBody
    protected HttpRestResult dbExceptionHandler(DaoException exception) {
        logErrorRequest(exception);
        return responseFail(ResultCode.DAO_EXCEPTION, null, exception.getMessage());
    }

    /**
     * service层服务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    protected HttpRestResult serviceExceptionHandler(ServiceException exception) {
        logErrorRequest(exception);
        return responseFail(ResultCode.SERVICE_EXCEPTION, null, exception.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler({HttpClientErrorException.class, IOException.class, Exception.class})
    @ResponseBody
    protected HttpRestResult commonExceptionHandler(Exception exception) {
        logErrorRequest(exception);
        return responseSystemException(ResultCode.SYSTEM_EXCEPTION);
    }

}
