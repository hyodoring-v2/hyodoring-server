package com.v2.hyodoring.family.application.family.domain.exception;

import com.v2.hyodoring.account.application.base.BaseException;

public class FamilyException extends BaseException {
    public FamilyException(FamilyErrorResponse errorResponse) {
        super(errorResponse);
    }
}
