package com.pms.utility;

public class MessageSender {

    public static void sendMessage(String to, String subject, String body) {
        Thread t = new Thread(() -> SendMail.send(to, subject, body));

        t.setDaemon(true);
        t.start();
    }

    public static void sendMessageText(String email, String subject, String body) {
        Thread t = new Thread(() -> SendMail.sendText(email, subject, body));

        t.setDaemon(true);
        t.start();
    }
}
