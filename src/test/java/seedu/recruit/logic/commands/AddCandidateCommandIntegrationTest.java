package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCandidateCommand}.
 */
public class AddCandidateCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Candidate validCandidate = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());
        expectedModel.addCandidate(validCandidate);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(new AddCandidateCommand(validCandidate), model, commandHistory,
                String.format(AddCandidateCommand.MESSAGE_SUCCESS, validCandidate), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Candidate candidateInList = model.getCandidateBook().getCandidatelist().get(0);
        assertCommandFailure(new AddCandidateCommand(candidateInList), model, commandHistory,
                AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }
