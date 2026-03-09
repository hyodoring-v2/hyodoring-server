package com.v2.hyodoring.family.application.notification.domain;

import com.v2.hyodoring.account.application.base.BaseException;

public class NotificationException extends BaseException {
    public NotificationException(NotificationErrorResponse errorResponse) {
        super(errorResponse);
    }
}
