package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompanies.getTypicalCompanyBook;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.Company;
import seedu.recruit.testutil.CompanyBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCompanyCommand}.
 */

public class AddCompanyCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_newCompany_success() {
        Company validCompany = new CompanyBuilder().build();

        Model expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.addCompany(validCompany);
        expectedModel.commitCompanyBook();

        assertCommandSuccess(new AddCompanyCommand(validCompany), model, commandHistory,
                String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany), expectedModel);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company companyInList = model.getCompanyBook().getCompanyList().get(0);
        assertCommandFailure(new AddCompanyCommand(companyInList), model, commandHistory,
                AddCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }




}
