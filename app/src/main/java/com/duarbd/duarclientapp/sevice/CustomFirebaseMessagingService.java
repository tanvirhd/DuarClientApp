package com.duarbd.duarclientapp.sevice;

import androidx.annotation.NonNull;

import com.duarbd.duarclientapp.tools.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService  extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage!=null){
            String title=remoteMessage.getNotification().getTitle();
            String message_body=remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(),title,message_body);
        }
    }
}
