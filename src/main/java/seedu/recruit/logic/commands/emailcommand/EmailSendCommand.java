package seedu.recruit.logic.commands.emailcommand;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * 4th step of the email command: send the email.
 */
public class EmailSendCommand extends Command {

    public static final String MESSAGE_USAGE = "Type \"send\" to send the message\n"
            + "Type \"preview\" to preview the email.\n"
            + "Type \"back\" to go back to select contents command.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email. Either you are not authorised or "
            + "you are not connected to the internet.";
    public static final String EMAIL_SEND_SHOWING_PREVIEW_MESSAGE = "Opened preview.\n";

    /**
     * Global instance of the scopes required by this the email command
     * If modifying these scopes, delete your previously saved tokens/ folder.
     *
     */
    //DEFAULT_FROM is "me" to work with any gmail account which was authenticated.
    protected static final String DEFAULT_FROM = "me";
    protected static final String APPLICATION_NAME = "RecruitBook";
    protected static final String TOKENS_DIRECTORY_PATH = "tokens";
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    protected static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_COMPOSE);
    protected static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static ArrayList<Candidate> candidateRecipients;
    private static ArrayList<JobOffer> jobOfferRecipients;
    private static ArrayList<Candidate> candidateContents;
    private static ArrayList<JobOffer> jobOfferContents;

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs)
            throws ParseException {
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Updates the recipients and contents array list in this class from emailUtil
     * @param emailUtil
     */
    protected void updateRecipientsAndContents(EmailUtil emailUtil) {
        if (emailUtil.isAreRecipientsCandidates()) {
            candidateRecipients = new ArrayList<>(emailUtil.getCandidates());
            jobOfferContents = new ArrayList<>(emailUtil.getJobOffers());
        } else {
            jobOfferRecipients = new ArrayList<>(emailUtil.getJobOffers());
            candidateContents = new ArrayList<>(emailUtil.getCandidates());
        }
    }

    /**
     * Resets the array lists in this class, to be used when email has been sent or cancelled
     */
    public static void resetRecipientsAndContents() {
        candidateRecipients = new ArrayList<>();
        jobOfferRecipients = new ArrayList<>();
        candidateContents = new ArrayList<>();
        jobOfferContents = new ArrayList<>();
    }

    /**
     * Generates a hashset of emails
     * @param recipientEmails hashset of emails
     * @param model
     * @param emailUtil
     */
    public void generateRecipients(Set<String> recipientEmails, Model model, EmailUtil emailUtil) {
        if (emailUtil.isAreRecipientsCandidates()) {
            //recipients are candidates
            for (Candidate candidateRecipient : candidateRecipients) {
                recipientEmails.add(candidateRecipient.getEmail().toString());
            }
        } else {
            //recipients are companies
            for (JobOffer jobOfferRecipient : jobOfferRecipients) {
                int index = model.getCompanyIndexFromName(jobOfferRecipient.getCompanyName());
                //Company not found in CompanyBook, prevent null pointer exception
                if (index == -1) {
                    continue;
                }

                Company company = model.getCompanyFromIndex(index);
                recipientEmails.add(company.getEmail().toString());
            }
        }
    }

    /**
     * Generates bodytext of the email.
     * @param emailUtil
     * @return bodytext String
     */
    public String generateContent(EmailUtil emailUtil) {
        StringBuilder bodyText;
        if (emailUtil.isAreRecipientsCandidates()) {
            bodyText = new StringBuilder(emailUtil.getEmailSettings().getBodyTextCandidateAsRecipient());
            //contents are companies
            for (JobOffer jobOfferContent : jobOfferContents) {
                bodyText.append('\n').append("Company: ").append(jobOfferContent.getCompanyName().toString());
                bodyText.append('\n').append("Job: ").append(jobOfferContent.getJob().toString());
                bodyText.append('\n').append("Salary offered: ").append(jobOfferContent.getSalary().toString());
                bodyText.append('\n');
            }
        } else {
            ArrayList<String> jobNames = new ArrayList<>();
            for (JobOffer jobOfferRecipient : jobOfferRecipients) {
                jobNames.add(jobOfferRecipient.getJob().toString());
            }

            bodyText = new StringBuilder(emailUtil.getEmailSettings().getBodyTextCompanyAsRecipient()
                    + jobNames.toString() + '\n');
            //contents are candidates
            for (Candidate candidateContent : candidateContents) {
                bodyText.append('\n').append("Name: ").append(candidateContent.getName().toString());
                bodyText.append('\n').append("Age: ").append(candidateContent.getAge().toString());
                bodyText.append('\n').append("Education: ").append(candidateContent.getEducation().toString());
                bodyText.append('\n').append("Email: ").append(candidateContent.getEmail().toString());
                bodyText.append('\n').append("Phone Number: ").append(candidateContent.getPhone().toString());
                bodyText.append('\n');
            }
        }
        return bodyText.toString();
    }

    /**
     * Function to generate a subject for email
     * @param emailUtil
     * @return String subject
     */
    public String generateSubject(EmailUtil emailUtil) {
        String subject;
        if (emailUtil.isAreRecipientsCandidates()) {
            subject = emailUtil.getEmailSettings().getSubjectCandidateAsRecipient();
        } else {
            subject = emailUtil.getEmailSettings().getSubjectCompanyAsRecipient();
        }
        return subject;
    }

    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = EmailUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .setApprovalPrompt("auto")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Initialiser for Gmail Service
     */
    public static Gmail serviceInit() throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        // Create a new authorized Gmail API client
        return new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param from sender of the email
     * @param to email of the receiver
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String from,
                                          String to,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props;
        props = System.getProperties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param from sender of the email
     * @param to ArrayList of the emails of the receivers
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage used to send an email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String from,
                                          Set<String> to,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props;
        props = System.getProperties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));

        for (String recipient : to) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress(recipient));
        }
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email recruit. The special value "me"
     *                can be used to indicate the authenticated user.
     * @param email   Email to be sent.
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(email);
        service.users().messages().send(userId, message).execute();
    }

    /**
     * Create a Message from an email
     *
     * @param email Email to be set to raw of message
     * @return Message containing base64url encoded email.
     * @throws IOException
     * @throws MessagingException
     */
    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
