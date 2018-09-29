package seedu.address.commons.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Helper function for sending emails.
 */
public class EmailUtil {

    private String mailServerHost = "smtp.gmail.com";
    private String mailServerPort = "587";
    private String userEmailAddress = "placeholder@gmail.com";
    private String userEmailPassword = "password";

    /**
     * Sets the SMTP server host.
     * @param mailServerHost
     */
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    /**
     * Sets the SMTP server port.
     * @param mailServerPort
     */
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    /**
     * Sets the email address for the email account.
     * @param userEmailAddress
     */
    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    /**
     * Sets the password for the email account.
     * @param userEmailPassword the password of the email account.
     */
    public void setUserEmailPassword(String userEmailPassword) {
        this.userEmailPassword = userEmailPassword;
    }

    /**
     * Sends an email to the recipients with subject and message.
     * @param recipient the list of recipient email addresses.
     * @param subject the subject of the email.
     * @param message the content of the email.
     * @throws MessagingException If an error occurs during message sending.
     */
    public void sendEmail(String[] recipient, String subject, String message) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailServerHost);
        properties.put("mail.smtp.port", mailServerPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmailAddress, userEmailPassword);
            }
        };

        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userEmailAddress));
        for (String addressee: recipient) {
            InternetAddress[] current = { new InternetAddress(addressee) };
            msg.addRecipients(Message.RecipientType.TO, current);
        }
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);
        Transport.send(msg);

    }
}
