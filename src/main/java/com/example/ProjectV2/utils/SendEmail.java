package com.example.ProjectV2.utils;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Person;
import com.example.ProjectV2.exception.UnAvailableServiceException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Locale;


@Component
public  class SendEmail {

    private final JavaMailSender mailSender;

    public SendEmail (JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    String fromAddress = "noreply@gamil.com";
    String senderName = "Home Service Provider";
    String subject = "Please verify your registration";
    String content =
            "Dear %s ,Please click the link below to verify your registration:"
            + "\n http://localhost:8080/save-user/%s-verify?code=%s"
            + "\nThank you"
            + "\nHome Service Provider.";


    public <E extends  Person> void sendVerificationEmail(E e , String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = e.getEmail();


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", e.getFirstName());
        String verifyURL = siteURL + "/verify?code=" + e.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content,true);

        mailSender.send(message);

    }

    public <E extends  Person>  void sendSimpleMessage(E e) {

        content = String.format(content,e.getFirstName(),e.getClass().getSimpleName().toLowerCase(), e.getVerificationCode());
        try {
            Unirest.post("https://api.mailgun.net/v3/sandbox46788d8a61f249acb288b2ca69952b92.mailgun.org"
                            + "/messages").
                    basicAuth("api", "5fda177d4675b244294156023496eaff-4c2b2223-fad81764")
                    .queryString("from", "noreply@localhost")
                    .queryString("to", e.getEmail())
                    .queryString("subject", subject)
                    .queryString("text", content )
                    .asJson();
        } catch (UnirestException ex) {
            throw new UnAvailableServiceException("");
        }
    }
}
