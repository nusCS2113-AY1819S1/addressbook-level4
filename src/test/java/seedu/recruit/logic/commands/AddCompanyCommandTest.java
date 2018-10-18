package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.company.Company;
import seedu.recruit.testutil.CompanyBuilder;


public class AddCompanyCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCompanyCommand(null);
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompanyAdded modelStub = new ModelStubAcceptingCompanyAdded();
        Company validCompany = new CompanyBuilder().build();

        CommandResult commandResult = new AddCompanyCommand(validCompany).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCompany), modelStub.companiesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() throws Exception {
        Company validCompany = new CompanyBuilder().build();
        AddCompanyCommand addCommand = new AddCompanyCommand(validCompany);
        CommandTestUtil.ModelStub modelStub = new ModelStubWithCompany(validCompany);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Company kfc = new CompanyBuilder().withCompanyName("kfc").build();
        Company mcdonalds = new CompanyBuilder().withCompanyName("mcdonalds").build();
        AddCompanyCommand addKfcCommand = new AddCompanyCommand(kfc);
        AddCompanyCommand addMcdonaldsCommand = new AddCompanyCommand(mcdonalds);

        // same object -> returns true
        assertTrue(addKfcCommand.equals(addKfcCommand));

        // same values -> returns true
        AddCompanyCommand addKfcCommandCopy = new AddCompanyCommand(kfc);
        assertTrue(addKfcCommand.equals(addKfcCommandCopy));

        // different types -> returns false
        assertFalse(addKfcCommand.equals(1));

        // null -> returns false
        assertFalse(addKfcCommand.equals(null));

        // different candidate -> returns false
        assertFalse(addKfcCommand.equals(addMcdonaldsCommand));
    }

    /**
     * A Model stub that contains a single company.
     */
    private class ModelStubWithCompany extends CommandTestUtil.ModelStub {
        private final Company company;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            this.company = company;
        }

        @Override
        public boolean hasCompany(Company candidate) {
            requireNonNull(candidate);
            return this.company.isSameCompany(candidate);
        }
    }

    /**
     * A Model stub that always accept the candidate being added.
     */
    private class ModelStubAcceptingCompanyAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Company> companiesAdded = new ArrayList<>();

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companiesAdded.stream().anyMatch(company::isSameCompany);
        }

        @Override
        public void addCompany(Company company) {
            requireNonNull(company);
            companiesAdded.add(company);
        }

        @Override
        public void commitCompanyBook() {
            // called by {@code AddCompanyCommand#execute()}
        }

        @Override
        public ReadOnlyCompanyBook getCompanyBook() {
            return new CompanyBook();
        }
    }

}
