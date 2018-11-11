package seedu.recruit.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

class EmailUtilTest {
    private CandidateBuilder candidateBuilder = new CandidateBuilder();
    private Candidate testCandidate = candidateBuilder.build();
    private JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
    private JobOffer testJobOffer = jobOfferBuilder.build();
    private EmailUtil emailUtil = new EmailUtil();

    @Test
    void addCandidate() {
        int currentSize = emailUtil.getCandidates().size();
        emailUtil.addCandidate(testCandidate);
        assertEquals(currentSize + 1, emailUtil.getCandidates().size());
    }

    @Test
    void addJobOffer() {
        int currentSize = emailUtil.getJobOffers().size();
        emailUtil.addJobOffer(testJobOffer);
        assertEquals(currentSize + 1, emailUtil.getJobOffers().size());
    }

    @Test
    void getRecipientJobOfferName() {
        String testName = emailUtil.getRecipientJobOfferName(testJobOffer);
        StringBuilder actualName = new StringBuilder();
        actualName.append(testJobOffer.getCompanyName().toString());
        actualName.append(" regarding job offer: ");
        actualName.append(testJobOffer.getJob().toString());
        assertEquals(true, actualName.toString().equals(testName));
    }

    @Test
    void getContentJobOfferName() {
        String testName = emailUtil.getContentJobOfferName(testJobOffer);
        StringBuilder actualName = new StringBuilder();
        actualName.append(testJobOffer.getJob().toString());
        actualName.append(" at ");
        actualName.append(testJobOffer.getCompanyName().toString());
        assertEquals(true, actualName.toString().equals(testName));
    }
}
