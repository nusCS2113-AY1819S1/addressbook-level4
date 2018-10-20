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

import seedu.address.model.email.Domain;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Person;


/**
 * Helper function for sending emails.
 */
public class EmailUtil {

    private static final String INVALID_DOMAIN = "This domain is currently not supported";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_TRUST = "mail.smtp.ssl.trust";
    private static String userEmailAddress = "";
    private static String userEmailPassword = "";

    /**
     * Sets the email address for the email account.
     *
     * @param email the email address of the email account.
     */
    public static void setUserEmailAddress(String email) {
        userEmailAddress = email;
    }

    /**
     * Sets the password for the email account.
     *
     * @param password the password of the email account.
     */
    public static void setUserEmailPassword(String password) {
        userEmailPassword = password;
    }

    /**
     * Sends an email to the recipients with subject and message.
     *
     * @param recipient the list of recipient email addresses.
     * @param subject   the subject of the email.
     * @param message   the content of the email.
     * @throws MessagingException If an error occurs during message sending.
     */
    public static void sendEmail(List<Person> recipient, Subject subject, seedu.address.model.email.Message message)
            throws MessagingException {

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmailAddress, userEmailPassword);
            }
        };

        Session session = Session.getInstance(setServer(), auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userEmailAddress));
        for (Person addressee : recipient) {
            InternetAddress[] current = {new InternetAddress(addressee.getEmail().toString())};
            msg.addRecipients(Message.RecipientType.TO, current);
        }
        msg.setSubject(subject.toString());
        msg.setSentDate(new Date());
        msg.setText(message.toString());
        Transport.send(msg);
    }

    /**
     * Checks if login credential for email account is provided.
     *
     * @return true if both userEmailAddress and userEmailPassword is non-empty.
     */
    public static boolean hasLoginCredentials() {
        return (!userEmailAddress.isEmpty()) || (!userEmailPassword.isEmpty());
    }

    /**
     * Sets the appropriate SMTP server properties based on {@code userEmailAddress}.
     *
     * @return Properties for sending email via correct SMTP server
     * @throws MessagingException if domain of {@code userEmailAddress} does not exist.
     */
    private static Properties setServer() throws MessagingException {
        Properties properties = new Properties();
        String mailServerHost;
        String mailServerPort;

        if (userEmailAddress.contains(Domain.GMAIL)) {
            mailServerHost = Domain.GMAIL_HOST;
            mailServerPort = Domain.SMTP_PORT;
            properties.put(MAIL_SMTP_TRUST, Domain.GMAIL_HOST);
        } else if (userEmailAddress.contains(Domain.OUTLOOK) || userEmailAddress.contains(Domain.HOTMAIL)
                || userEmailAddress.contains(Domain.LIVE)) {
            mailServerHost = Domain.LIVE_HOST;
            mailServerPort = Domain.SMTP_PORT;
            properties.put(MAIL_SMTP_TRUST, Domain.LIVE_HOST);
        } else if (userEmailAddress.contains(Domain.OFFICE365) || userEmailAddress.contains(Domain.NUS_STUDENT)) {
            mailServerHost = Domain.OFFICE365_HOST;
            mailServerPort = Domain.SMTP_PORT;
            properties.put(MAIL_SMTP_TRUST, Domain.OFFICE365_HOST);
        } else if (userEmailAddress.contains(Domain.NUS_STAFF)) {
            mailServerHost = Domain.NUS_STAFF_HOST;
            mailServerPort = Domain.SMTP_PORT;
            properties.put(MAIL_SMTP_TRUST, Domain.NUS_STAFF_HOST);
        } else {
            throw new MessagingException(INVALID_DOMAIN);
        }

        properties.put(MAIL_SMTP_HOST, mailServerHost);
        properties.put(MAIL_SMTP_PORT, mailServerPort);
        properties.put(MAIL_SMTP_AUTH, "true");
        properties.put(MAIL_SMTP_STARTTLS, "true");

        return properties;
    }
}
