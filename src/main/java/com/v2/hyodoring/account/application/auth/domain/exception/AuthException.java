package com.v2.hyodoring.account.application.auth.domain.exception;

import com.v2.hyodoring.account.application.base.BaseException;

public class AuthException extends BaseException {
    public AuthException(AuthErrorResponse response) {
        super(response);
    }
}
