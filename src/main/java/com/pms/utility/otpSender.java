package com.pms.utility;


import java.util.Base64;
import java.util.Random;

public class otpSender {
    private static final String subject = "OTP for Project Management System Login";
    private static final int low = 100000;
    private static final int high = 1000000;

    public static String sendOtpEmail(String username, String email) {
        String message = "Dear " + username + ", this is your One Time Password in order to reset your Project Management System Account .<br/>";
        Random r = new Random();
        int result = r.nextInt(high - low) + low;
        message = message + "\n" + result;

        final String messageSent = message;
        Thread t = new Thread(() -> SendMail.send(email, subject, messageSent));
        t.setDaemon(true);
        t.start();

        String s = BCrypt.hashpw(String.valueOf(result), BCrypt.gensalt());

        return Base64.getMimeEncoder().encodeToString(s.getBytes());
    }
}
