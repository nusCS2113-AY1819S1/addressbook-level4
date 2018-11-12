package seedu.recruit.commons.core;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for emailsettings class
 */
public class EmailSettingsTest {
    private static final String DEFAULT_SUBJECT_CANDIDATE_AS_RECIPIENT = "New job offers that I have found for you!";
    private static final String DEFAULT_SUBJECT_COMPANY_AS_RECIPIENT = "New candidates found for your company!";
    private static final String DEFAULT_BODYTEXT_CANDIDATE_AS_RECIPIENT = "Dear candidate,\n"
            + "I think you will be interested in these job offers!";
    private static final String DEFAULT_BODYTEXT_COMPANY_AS_RECIPIENT = "Dear Sir/Madam,\n"
            + "I think you will be interested in these candidates.";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EmailSettings customEmailSettings = new EmailSettings("test1", "test2", "test3", "test4");
    private EmailSettings defaultEmailSettings = new EmailSettings();

    @Test
    public void getSubjectCandidateAsRecipient_emailSettings_customObject() {
        assertTrue(customEmailSettings.getSubjectCandidateAsRecipient().equals("test1"));
    }

    @Test
    public void getSubjectCompanyAsRecipient_emailSettings_customObject() {
        assertTrue(customEmailSettings.getSubjectCompanyAsRecipient().equals("test2"));
    }

    @Test
    public void getBodyTextCandidateAsRecipient_emailSettings_customObject() {
        assertTrue(customEmailSettings.getBodyTextCandidateAsRecipient().equals("test3"));
    }

    @Test
    public void getBodyTextCompanyAsRecipient_emailSettings_customObject() {
        assertTrue(customEmailSettings.getBodyTextCompanyAsRecipient().equals("test4"));
    }

    @Test
    public void toString_emailSettings_customObject() {
        String correctString = "Subject for Candidates as recipients : test1\n"
                + "Subject for Companies as recipients : test2\n"
                + "Body text for Candidates as recipients : test3\n"
                + "Body text for Companies as recipients : test4";
        assertTrue(customEmailSettings.toString().equals(correctString));
    }

    @Test
    public void getSubjectCandidateAsRecipient_emailSettings_defaultObject() {
        assertTrue(defaultEmailSettings.getSubjectCandidateAsRecipient()
                .equals(DEFAULT_SUBJECT_CANDIDATE_AS_RECIPIENT));
    }

    @Test
    public void getSubjectCompanyAsRecipient_emailSettings_defaultObject() {
        assertTrue(defaultEmailSettings.getSubjectCompanyAsRecipient().equals(DEFAULT_SUBJECT_COMPANY_AS_RECIPIENT));
    }

    @Test
    public void getBodyTextCandidateAsRecipient_emailSettings_defaultObject() {
        assertTrue(defaultEmailSettings.getBodyTextCandidateAsRecipient()
                .equals(DEFAULT_BODYTEXT_CANDIDATE_AS_RECIPIENT));
    }

    @Test
    public void getBodyTextCompanyAsRecipient_emailSettings_defaultObject() {
        assertTrue(defaultEmailSettings.getBodyTextCompanyAsRecipient().equals(DEFAULT_BODYTEXT_COMPANY_AS_RECIPIENT));
    }

    @Test
    public void toString_emailSettings_defaultObject() {
        String correctString = "Subject for Candidates as recipients : "
                + DEFAULT_SUBJECT_CANDIDATE_AS_RECIPIENT + '\n'
                + "Subject for Companies as recipients : "
                + DEFAULT_SUBJECT_COMPANY_AS_RECIPIENT + '\n'
                + "Body text for Candidates as recipients : "
                + DEFAULT_BODYTEXT_CANDIDATE_AS_RECIPIENT + '\n'
                + "Body text for Companies as recipients : "
                + DEFAULT_BODYTEXT_COMPANY_AS_RECIPIENT;
        assertTrue(defaultEmailSettings.toString().equals(correctString));
    }
}
