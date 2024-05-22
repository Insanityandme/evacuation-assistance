package com.evac.security.services;

import com.evac.payload.request.NotificationPayload;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is a service for FirebaseMessaging. This class makes push notifications possible because it sends it.
 */
@Service
public class FirebaseMessagingService {

    private FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(){

    }

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging){
        this.firebaseMessaging = firebaseMessaging;
    }

    /**
     * This method sends a custom push notification using the NotificationPayload class as the notification structure.
     * @param payload -> notification structure
     * @return a String with the confirmation that the message has been sent.
     * @throws FirebaseMessagingException -> Exception that handles everything related to FirebaseMessaging.
     */
    public String sendCustomNotification(NotificationPayload payload) throws FirebaseMessagingException{

        Notification notification = Notification.builder()
                .setTitle(payload.getNotification().getTitle())
                .setBody(payload.getNotification().getBody())
                .build();

        //Create a message builder
        Message.Builder builder = Message.builder()
                .setToken(payload.getTo())
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setChannelId(payload.getNotification().getAndroid_channel_id())
                                .build())
                        .build());

        //Send the message using FirebaseMessaging
        String response = firebaseMessaging.send(builder.build());
        return response;

    }

    /**
     * This method send push-notifications till de olika tokens som är registrerade i databasen. This use table where all the devices
     * are stored.
     * @param tokens -> Tokens that Firebase service will send push-notifications to.
     * @throws FirebaseMessagingException
     */
    public void sendToMultipleDevices(List<String> tokens) throws FirebaseMessagingException{
        Notification notification = Notification.builder()
                .setTitle("ALARM - EVACUATION")
                .setBody("Are you available?")
                .build();

        //Create a multicastMessage object

        MulticastMessage.Builder multicastMessage = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(tokens)
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setChannelId("custom_channel")
                                .build())
                        .build());

        firebaseMessaging.sendMulticast(multicastMessage.build());
    }
}
