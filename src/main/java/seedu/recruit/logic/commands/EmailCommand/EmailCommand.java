package seedu.recruit.logic.commands.EmailCommand;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javafx.collections.ObservableList;

import com.google.api.services.gmail.Gmail;

import seedu.recruit.commons.core.Email;

/**
 * Stores variables pertaining to the EmailCommand multistep
 */
public class EmailCommand {

    private static Gmail service;
    static {
        try {
            service = Email.init();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
    private ObservableList<?> recipients;
    private ObservableList<?> contents;
    private boolean areRecipientsCandidates;
    private String to;
    private String subject;
    private String bodyText;
    public static final String DEFAULT_FROM = "cs2113.f09.4@gmail.com";

    /**
     * Getters and Setters
     */
    public static Gmail getService() {
        return service;
    }

    public static void setService(Gmail service) {
        EmailCommand.service = service;
    }

    public ObservableList<?> getRecipients() {
        return recipients;
    }

    public void setRecipients(ObservableList<?> recipients) {
        this.recipients = recipients;
    }

    public ObservableList<?> getContents() {
        return contents;
    }

    public void setContents(ObservableList<?> contents) {
        this.contents = contents;
    }

    public boolean isAreRecipientsCandidates() {
        return areRecipientsCandidates;
    }

    public void setAreRecipientsCandidates(boolean areRecipientsCandidates) {
        this.areRecipientsCandidates = areRecipientsCandidates;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
}
