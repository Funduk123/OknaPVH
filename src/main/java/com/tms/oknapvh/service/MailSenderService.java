package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.ContactForm;

public interface MailSenderService {

    String sendPasswordResetEmail(String userEmail);

    void sendAnonymousSupportEmail(String email, String message);

    void sendLoggedInSupportEmail(String message);

    void sendContactForm(ContactForm contactForm);

}
