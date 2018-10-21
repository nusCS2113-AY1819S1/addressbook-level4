package seedu.recruit.commons.core;

import java.io.Serializable;

/**
 * Stores settings for email command. Can be changed in preferences.json
 */
public class EmailSettings implements Serializable {

    private static final String DEFAULT_SUBJECT_CANDIDATE_AS_RECIPIENT = "New job offers that I have found for you!";
    private static final String DEFAULT_SUBJECT_COMPANY_AS_RECIPEINT = "New candidates found for your company!";
    private static final String DEFAULT_BODYTEXT_CANDIDATE_AS_RECIPIENT = "Dear candidate,\n"
            + "I think you will be interested in these job offers!";
    private static final String DEFAULT_BODYTEXT_COMPANY_AS_RECIPIENT = "Dear Sir/Madam,\n"
            + "I think you will be interested in these candidates.";

    private final String subjectCandidateAsRecipient;
    private final String subjectCompanyAsRecipient;
    private final String bodyTextCandidateAsRecipient;
    private final String bodyTextCompanyAsRecipient;

    public EmailSettings() {
        subjectCandidateAsRecipient = DEFAULT_SUBJECT_CANDIDATE_AS_RECIPIENT;
        subjectCompanyAsRecipient = DEFAULT_SUBJECT_COMPANY_AS_RECIPEINT;
        bodyTextCandidateAsRecipient = DEFAULT_BODYTEXT_CANDIDATE_AS_RECIPIENT;
        bodyTextCompanyAsRecipient = DEFAULT_BODYTEXT_COMPANY_AS_RECIPIENT;
    }

    public EmailSettings(String subjectCandidateAsRecipient,
                         String subjectCompanyAsRecipient,
                         String bodyTextCandidateAsRecipient,
                         String bodyTextCompanyAsRecipient) {
        this.subjectCandidateAsRecipient = subjectCandidateAsRecipient;
        this.subjectCompanyAsRecipient  = subjectCompanyAsRecipient;
        this.bodyTextCandidateAsRecipient = bodyTextCandidateAsRecipient;
        this.bodyTextCompanyAsRecipient = bodyTextCompanyAsRecipient;
    }

    public String getSubjectCandidateAsRecipient() {
        return subjectCandidateAsRecipient;
    }

    public String getSubjectCompanyAsRecipient() {
        return subjectCompanyAsRecipient;
    }

    public String getBodyTextCandidateAsRecipient() {
        return bodyTextCandidateAsRecipient;
    }

    public String getBodyTextCompanyAsRecipient() {
        return bodyTextCompanyAsRecipient;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Subject for Candidates as recipients : " + subjectCandidateAsRecipient + "\n");
        output.append("Subject for Companies as recipients : " + subjectCompanyAsRecipient + "\n");
        output.append("Body text for Candidates as recipients : " + bodyTextCandidateAsRecipient + "\n");
        output.append("Body text for Companies as recipients : " + bodyTextCompanyAsRecipient);
        return output.toString();
    }
}
