package com.shri.springify.Springify.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
 @RequiredArgsConstructor
public class EmailService {

    private  final JavaMailSender javaMailSender;

    public  void sendVerificationEmail(String userEmail,String otp, String subject,String text) throws Exception {
        try{
             MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"utf-8");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setTo(userEmail);

            javaMailSender.send(mimeMessage);

        }
        catch (Exception e)
        {
              throw  new Exception("Failed to send mail");
        }
    }
}
