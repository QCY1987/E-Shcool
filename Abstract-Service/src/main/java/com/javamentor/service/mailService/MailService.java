package com.javamentor.service.mailService;


import com.javamentor.model.user.User;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {
    void sendToUser(User user, String message, String subject);
    void sendToEmail(String email, String message, String subject);

    void sendToEmailUsingTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException;

    void sendToUserUsingTemplate(
            User to, String subject, Map<String, Object> templateModel);
}
