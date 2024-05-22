package com.evac.payload.request;

/**
 * This class is a payload of the notification structure that will go into the http-request.
 */
public class NotificationPayload {

    private String to; //Token
    private Notification notification; //Notification (static class)

    public NotificationPayload(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public static class Notification {
        private String title;
        private String body;
        private String sound;
        private String android_channel_id;

        public Notification(String title, String body, String sound, String android_channel_id) {
            this.title = title;
            this.body = body;
            this.sound = sound;
            this.android_channel_id = android_channel_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public void setAndroid_channel_id(String android_channel_id) {
            this.android_channel_id = android_channel_id;
        }

        public String getAndroid_channel_id() {
            return android_channel_id;
        }
    }
}
