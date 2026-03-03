package com.v2.hyodoring.account.application.account.domain.exception;

import com.v2.hyodoring.account.application.base.BaseException;
import com.v2.hyodoring.account.application.base.ErrorResponse;

public class AccountException extends BaseException {
  public AccountException(ErrorResponse errorResponse) {
    super(errorResponse);
  }
}
