package com.v2.hyodoring.family.infrastructure.fcm.base;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.v2.hyodoring.family.application.notification.domain.NotificationErrorResponse;
import com.v2.hyodoring.family.application.notification.domain.NotificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FCMClient {
    private final FirebaseMessaging firebaseMessaging;

    public void sendMessage(String token, String title, String body) {
        try {
            firebaseMessaging.send(Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setToken(token)
                    .build());
        } catch (FirebaseMessagingException e) {
            throw new NotificationException(NotificationErrorResponse.FIREBASE_MESSAGING_FAILED);
        }
    }
}
