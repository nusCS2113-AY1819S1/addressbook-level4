package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.NameContainsKeywordsPredicate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Candidates
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_AGE_AMY = "21";
    public static final String VALID_AGE_BOB = "22";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_JOB_AMY = "Dancer";
    public static final String VALID_JOB_BOB = "Manager";
    public static final String VALID_EDUCATION_AMY = "A Level";
    public static final String VALID_EDUCATION_BOB = "Business Masters";
    public static final String VALID_SALARY_AMY = "3000";
    public static final String VALID_SALARY_BOB = "3500";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String AGE_DESC_AMY = " " + PREFIX_AGE + VALID_AGE_AMY;
    public static final String AGE_DESC_BOB = " " + PREFIX_AGE + VALID_AGE_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String JOB_DESC_AMY = " " + PREFIX_JOB + VALID_JOB_AMY;
    public static final String JOB_DESC_BOB = " " + PREFIX_JOB + VALID_JOB_BOB;
    public static final String EDUCATION_DESC_AMY = " " + PREFIX_EDUCATION + VALID_EDUCATION_AMY;
    public static final String EDUCATION_DESC_BOB = " " + PREFIX_EDUCATION + VALID_EDUCATION_BOB;
    public static final String SALARY_DESC_AMY = " " + PREFIX_SALARY + VALID_SALARY_AMY;
    public static final String SALARY_DESC_BOB = " " + PREFIX_SALARY + VALID_SALARY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCandidateCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCandidateCommand.EditPersonDescriptor DESC_BOB;

    // Company
    public static final String VALID_NAME_ALFA = "Alfa Romeo Automobiles";
    public static final String VALID_NAME_BMW = "BMW AG";
    public static final String VALID_PHONE_ALFA = "33333333";
    public static final String VALID_PHONE_BMW = "44444444";
    public static final String VALID_EMAIL_ALFA = "alfa@example.com";
    public static final String VALID_EMAIL_BMW = "bmw@example.com";
    public static final String VALID_ADDRESS_ALFA = "Block 312, Alfa Romeo Street 1";
    public static final String VALID_ADDRESS_BMW = "Block 123, Bmw Street 3";

    public static final String NAME_DESC_ALFA = " " + PREFIX_COMPANY_NAME + VALID_NAME_ALFA;
    public static final String NAME_DESC_BMW = " " + PREFIX_COMPANY_NAME + VALID_NAME_BMW;
    public static final String PHONE_DESC_ALFA = " " + PREFIX_PHONE + VALID_PHONE_ALFA;
    public static final String PHONE_DESC_BMW = " " + PREFIX_PHONE + VALID_PHONE_BMW;
    public static final String EMAIL_DESC_ALFA = " " + PREFIX_EMAIL + VALID_EMAIL_ALFA;
    public static final String EMAIL_DESC_BMW = " " + PREFIX_EMAIL + VALID_EMAIL_BMW;
    public static final String ADDRESS_DESC_ALFA = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALFA;
    public static final String ADDRESS_DESC_BMW = " " + PREFIX_ADDRESS + VALID_ADDRESS_BMW;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (IOException | GeneralSecurityException | CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the recruit book and the filtered candidate list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CandidateBook expectedCandidateBook = new CandidateBook(actualModel.getCandidateBook());
        List<Candidate> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCandidateList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (IOException | GeneralSecurityException | CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCandidateBook, actualModel.getCandidateBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredCandidateList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the candidate at the given {@code targetIndex} in the
     * {@code model}'s recruit book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCandidateList().size());

        Candidate candidate = model.getFilteredCandidateList().get(targetIndex.getZeroBased());
        final String[] splitName = candidate.getName().fullName.split("\\s+");
        model.updateFilteredCandidateList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCandidateList().size());
    }

    /**
     * Deletes the first candidate in {@code model}'s filtered list from {@code model}'s recruit book.
     */
    public static void deleteFirstPerson(Model model) {
        Candidate firstCandidate = model.getFilteredCandidateList().get(0);
        model.deleteCandidate(firstCandidate);
        model.commitCandidateBook();
    }

    /**
     * A default model stub that have all of the methods failing.
     */

    public static class ModelStub implements Model {
        @Override
        public void addCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCandidateData(ReadOnlyCandidateBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCandidateBook getCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCandidate(Candidate target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCandidate(Candidate target, Candidate editedCandidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Candidate> getFilteredCandidateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCandidateList(Predicate<Candidate> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCompanyData(ReadOnlyCompanyBook newData) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ReadOnlyCompanyBook getCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCompany(Company target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCompany(Company target, Company editedCompany) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCompanyIndexFromName(CompanyName companyName) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public Company getCompanyFromIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJobOffer(CompanyName companyName, JobOffer jobOffer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyJobList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyJobList(Predicate<JobOffer> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
