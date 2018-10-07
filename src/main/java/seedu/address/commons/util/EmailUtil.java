package seedu.address.commons.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.model.email.Subject;
import seedu.address.model.person.Person;


/**
 * Helper function for sending emails.
 */
public class EmailUtil {
    /**
     * Todo: allow auto identification of SMTP servers based on provided email login. Make a login command.
     */
    private static String mailServerHost = "smtp.gmail.com";
    private static String mailServerPort = "587";
    private static String userEmailAddress = "tsurajovin@gmail.com";
    private static String userEmailPassword = "Tsurajovin!123";

    /**
     * Sets the SMTP server host.
     * @param host the hostname of the SMTP server.
     */
    public void setMailServerHost(String host) {
        mailServerHost = host;
    }

    /**
     * Sets the SMTP server port.
     * @param port the port of the SMTP server.
     */
    public void setMailServerPort(String port) {
        mailServerPort = port;
    }

    /**
     * Sets the email address for the email account.
     * @param email the email address of the email account.
     */
    public void setUserEmailAddress(String email) {
        userEmailAddress = email;
    }

    /**
     * Sets the password for the email account.
     * @param password the password of the email account.
     */
    public void setUserEmailPassword(String password) {
        userEmailPassword = password;
    }

    /**
     * Sends an email to the recipients with subject and message.
     * @param recipient the list of recipient email addresses.
     * @param subject the subject of the email.
     * @param message the content of the email.
     * @throws MessagingException If an error occurs during message sending.
     */
    public static void sendEmail(List<Person> recipient, Subject subject, seedu.address.model.email.Message message) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailServerHost);
        properties.put("mail.smtp.port", mailServerPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmailAddress, userEmailPassword);
            }
        };

        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userEmailAddress));
        for (Person addressee: recipient) {
            InternetAddress[] current = { new InternetAddress(addressee.getEmail().toString()) };
            msg.addRecipients(Message.RecipientType.TO, current);
        }
        msg.setSubject(subject.toString());
        msg.setSentDate(new Date());
        msg.setText(message.toString());
        Transport.send(msg);

    }
}
