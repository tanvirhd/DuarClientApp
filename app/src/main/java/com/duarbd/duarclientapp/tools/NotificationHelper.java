package com.duarbd.duarclientapp.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.duarbd.duarclientapp.R;

public class NotificationHelper {
    public static final String CHANNEL_ID ="duar_client_channel_id";
    public static final String CHANNEL_NAME="duar_client_channel";
    public static final String CHANNEL_DES="A warm welcome from Duar-Team";

    public static void displayNotification(Context context, String nTitle, String nBody){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DES);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(nTitle)
                .setContentText(nBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1,notificationBuilder.build());//id is used to delete or modify this notification

    }
}
