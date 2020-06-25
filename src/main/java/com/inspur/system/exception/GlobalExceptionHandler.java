package com.inspur.system.exception;

import com.inspur.system.response.ServerResponse;
import com.inspur.utils.ExceptionLogerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ServerResponse handle(BaseException e) {
        log.error(e.getMessage(), ExceptionLogerUtils.getErrmessage(e));
        return ServerResponse.createByErrorCodeMessage(getCode(e), getMessage(e));
    }

    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ServerResponse handleBaseException(BaseException e) {
        log.error(e.getMessage(), ExceptionLogerUtils.getErrmessage(e));
        return ServerResponse.createByErrorCodeMessage(getCode(e), getMessage(e));
    }

    /**
     * Controller层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse handleServletException(Exception e) {
        log.error(e.getMessage(), ExceptionLogerUtils.getErrmessage(e));
        return ServerResponse.createByErrorMessage(e.getMessage());
    }


    /**
     * 获取异常提示消息
     *
     * @param e 异常
     * @return
     */
    public String getMessage(BaseException e) {
        return e.getResponseEnum().getMessage();
    }

    /**
     * 获取异常代号
     *
     * @param e 异常
     * @return
     */
    public int getCode(BaseException e) {
        return e.getResponseEnum().getCode();
    }


}
