package com.javamentor.service.mailService;

import com.javamentor.model.user.User;
import com.javamentor.repository.mailTemplate.MailTemplateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    private final MailTemplateRepository mailTemplateRepository;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,  MailTemplateRepository mailTemplateRepository) {
        this.mailSender = mailSender;
        this.mailTemplateRepository = mailTemplateRepository;

    }

    @Override
    public void sendToUser(User user, String message, String subject) {
        String emailTo = user.getEmail();
        sendToEmail(emailTo, message, subject);
    }



    @Override
    public void sendToEmail(String to, String htmlBody, String subject) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }

    @Override
    public void sendToEmailUsingTemplate(
            String to, String subject, Map<String, Object> templateModel) {

        String template = mailTemplateRepository.getHtmlBodyByName("simple_mail");
        String emailBody = getTemplateFromAttributes(template, templateModel);
        System.out.println(emailBody);
        sendToEmail(to, emailBody, subject);

    }

    @Override
    public void sendToUserUsingTemplate(
            User to, String subject, Map<String, Object> templateModel) {

        String emailTo = to.getEmail();
        sendToEmailUsingTemplate(emailTo,  subject, templateModel);
    }



    public TemplateEngine getTemplateEngine() {

        templateEngine = new TemplateEngine();
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        templateEngine.setTemplateResolver(stringTemplateResolver);
        return templateEngine;
    }



    public String getTemplateFromAttributes(String htmlContent, Map<String, Object> attr) {

        templateEngine = getTemplateEngine();
        Context context = new Context();
        if (!CollectionUtils.isEmpty(attr)) {
            attr.forEach(context::setVariable);
        }
        return templateEngine.process(htmlContent, context);
    }


}
