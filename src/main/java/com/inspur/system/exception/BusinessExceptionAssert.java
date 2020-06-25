package com.inspur.system.exception;

import java.text.MessageFormat;

public interface BusinessExceptionAssert extends Assert, IResponseEnum {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }

}
