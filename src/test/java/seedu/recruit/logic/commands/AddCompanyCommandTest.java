package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.company.Company;



public class AddCompanyCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCompanyCommand(null);
    }


    /**
     * A Model stub that always accept the candidate being added.
     */
    private class ModelStubAcceptingCompanyAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Company> companyAdded = new ArrayList<>();

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companyAdded.stream().anyMatch(company::isSameCompany);
        }

        @Override
        public void addCompany(Company company) {
            requireNonNull(company);
            companyAdded.add(company);
        }

        @Override
        public void commitCandidateBook() {
            // called by {@code AddCompanyCommand#execute()}
        }

        @Override
        public ReadOnlyCompanyBook getCompanyBook() {
            return new CompanyBook();
        }
    }

}
