package com.artistcorner.engclasses.others.observer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendMail {

    private SendMail(){ throw new IllegalStateException("Utility class");}

    public static void send(String mail,String nameArt,String indirizzo,String nomegal) {

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
            message.setSubject("ArtistCorner, Proposal from ArtGallery");
            message.setText("Dear "+nameArt+","
                    + "\n\n"+"We just saw your Artworks uploaded on 'ArtistCorner', you seem like a very interesting artist and we would like to stay in touch for a possible collaboration. Let us know via 'ArtistCorner' if you would be interested. We find ourselves in "+indirizzo+"\nBest regards "+nomegal);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}