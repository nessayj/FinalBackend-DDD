package com.DDD.service;

import com.DDD.entity.Member;
import com.DDD.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private MemberRepository memberRepository;


    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "DDD-helloAccount@proton.me";

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String body){
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(FROM_ADDRESS);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body, "utf-8", "html");
        };
        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }

}
