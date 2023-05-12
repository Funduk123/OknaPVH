package com.tms.oknapvh.service;

public interface MailSenderService {

    String sendPasswordResetEmail(String userEmail);

    void sendAnonymousSupportEmail(String email, String message);

    void sendLoggedInSupportEmail(String message);

}
