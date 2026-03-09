package com.v2.hyodoring.family.application.greeting.domain.exception;

import com.v2.hyodoring.account.application.base.BaseException;

public class GreetingException extends BaseException {
    public GreetingException(GreetingErrorResponse errorResponse) {
        super(errorResponse);
    }
}
