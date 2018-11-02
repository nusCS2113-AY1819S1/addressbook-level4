package seedu.recruit.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CandidateCardHandle;
import guitests.guihandles.CandidateDetailsPanelHandle;
import guitests.guihandles.CompanyCardHandle;
import guitests.guihandles.CompanyJobDetailsPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    // ================================ COMPANY BOOK ================================================ //

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CandidateCardHandle expectedCard, CandidateCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getSalary(), actualCard.getSalary());
        assertEquals(expectedCard.getEducation(), actualCard.getEducation());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getJob(), actualCard.getJob());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCandidate}.
     */
    public static void assertCardDisplaysPerson(Candidate expectedCandidate, CandidateCardHandle actualCard) {
        assertEquals(expectedCandidate.getName().fullName, actualCard.getName());
        assertEquals(expectedCandidate.getEducation().value, actualCard.getEducation());
        assertEquals(expectedCandidate.getSalary().value, actualCard.getSalary());
        assertEquals(expectedCandidate.getJob().value, actualCard.getJob());
        assertEquals(expectedCandidate.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code candidateDetailsPanelHandle} displays the details of {@code candidates}
     * correctly and in the correct order.
     */
    public static void assertListMatching(CandidateDetailsPanelHandle candidateDetailsPanelHandle,
                                          Candidate... candidates) {
        for (int i = 0; i < candidates.length; i++) {
            candidateDetailsPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(candidates[i], candidateDetailsPanelHandle.getCandidateCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code candidateDetailsPanelHandle} displays the details of
     * {@code candidates} correctly and in the correct order.
     */
    public static void assertListMatching(CandidateDetailsPanelHandle candidateDetailsPanelHandle,
                                          List<Candidate> candidates) {
        assertListMatching(candidateDetailsPanelHandle, candidates.toArray(new Candidate[0]));
    }


    /**
     * Asserts the size of the list in {@code candidateDetailsPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CandidateDetailsPanelHandle candidateDetailsPanelHandle, int size) {
        int numberOfPeople = candidateDetailsPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    // ================================ COMPANY BOOK ================================================ //

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCompanyCardEquals(CompanyCardHandle expectedCard,
                                               CompanyCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getAddressId(), actualCard.getAddressId());
        assertEquals(expectedCard.getEmailId(), actualCard.getEmailId());
        assertEquals(expectedCard.getPhoneId(), actualCard.getPhoneId());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCompany}.
     */
    public static void assertCompanyCardDisplaysPerson(Company expectedCompany,
                                                       CompanyCardHandle actualCard) {
        assertEquals(expectedCompany.getCompanyName().value, actualCard.getName());
        assertEquals(expectedCompany.getAddress().value, actualCard.getAddressId());
        assertEquals(expectedCompany.getEmail().value, actualCard.getEmailId());
        assertEquals(expectedCompany.getPhone().value, actualCard.getPhoneId());
    }

    /**
     * Asserts that the list in {@code companyJobDetailsPanelHandle} displays the details of {@code companies}
     * correctly and in the correct order.
     */
    public static void assertCompanyListMatching(CompanyJobDetailsPanelHandle companyJobDetailsPanelHandle,
                                          Company... companies) {
        for (int i = 0; i < companies.length; i++) {
            companyJobDetailsPanelHandle.navigateToCard(i);
            assertCompanyCardDisplaysPerson(companies[i], companyJobDetailsPanelHandle.getCompanyCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code companyJobDetailsPanelHandle} displays the details of
     * {@code companies} correctly and in the correct order.
     */
    public static void assertCompanyListMatching(CompanyJobDetailsPanelHandle companyJobDetailsPanelHandle,
                                          List<Company> companies) {
        assertCompanyListMatching(companyJobDetailsPanelHandle, companies.toArray(new Company[0]));
    }

    /**
     * Asserts the size of the list in {@code companyJobDetailsPanelHandle} equals to {@code size}.
     */
    public static void assertCompanyListSize(CompanyJobDetailsPanelHandle companyJobDetailsPanelHandle, int size) {
        int numberOfCompanies = companyJobDetailsPanelHandle.getListSize();
        assertEquals(size, numberOfCompanies);
    }

    // ================================ RESULT DISPLAY ============================================== //
    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
