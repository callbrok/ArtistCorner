package com.artistcorner.engclasses.observer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendMail {

    public static void send(String mail,String nameBuy,String title) {

        final String username = "artist.corner.company@gmail.com";
        final String password = "ispw2021";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("artist.corner.company@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail));
            message.setSubject("ArtistCorner, Art Work Sold");
            message.setText("Dear Artist, "
                    + "\n\nWe inform You that your Art Work: "+ title +" has been purchased from "+nameBuy);

            Transport.send(message);

            System.out.println("Email sent to: "+mail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}