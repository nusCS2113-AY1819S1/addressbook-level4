package seedu.recruit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_ALFA;
import static seedu.recruit.testutil.TypicalCompanies.ALFA;
import static seedu.recruit.testutil.TypicalCompanies.getTypicalCompanyBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Ignore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.exceptions.DuplicateCompanyException;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CompanyBuilder;

@Ignore
public class CompanyBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CompanyBook companyBook = new CompanyBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), companyBook.getCompanyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        companyBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        CompanyBook newData = getTypicalCompanyBook();
        companyBook.resetData(newData);
        assertEquals(newData, companyBook);
    }

    @Test
    public void resetData_withDuplicateCompanies_throwsDuplicateCompanyException() {
        // Two companies with the same identity fields
        Company editedAlfa = new CompanyBuilder(ALFA).withAddress(VALID_ADDRESS_ALFA).withEmail(VALID_EMAIL_ALFA)
                .build();
        List<Company> newCompanies = Arrays.asList(ALFA, editedAlfa);
        CompanyBookStub newData = new CompanyBookStub(newCompanies);

        thrown.expect(DuplicateCompanyException.class);
        companyBook.resetData(newData);
    }

    @Test
    public void hasCompany_nullCompany_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        companyBook.hasCompany(null);
    }

    @Test
    public void hasCompany_companyNotInAddressBook_returnsFalse() {
        assertFalse(companyBook.hasCompany(ALFA));
    }

    @Test
    public void hasCompany_companyInAddressBook_returnsTrue() {
        companyBook.addCompany(ALFA);
        assertTrue(companyBook.hasCompany(ALFA));
    }

    @Test
    public void hasCompany_companyWithSameIdentityFieldsInAddressBook_returnsTrue() {
        companyBook.addCompany(ALFA);
        Company editedAlfa = new CompanyBuilder(ALFA).withAddress(VALID_ADDRESS_ALFA).withEmail(VALID_EMAIL_ALFA)
                .build();
        assertTrue(companyBook.hasCompany(editedAlfa));
    }

    @Test
    public void getCompanyList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        companyBook.getCompanyList().remove(0);
    }

    /**
     * A stub ReadOnlyCompanyBook whose companies list can violate interface constraints.
     */
    private static class CompanyBookStub implements ReadOnlyCompanyBook {
        private final ObservableList<Company> companies = FXCollections.observableArrayList();

        CompanyBookStub(Collection<Company> companies) {
            this.companies.setAll(companies);
        }

        @Override
        public ObservableList<Company> getCompanyList() {
            return companies;
        }

        @Override
        public ObservableList<JobOffer> getCompanyJobList() {
            return null;
        }
    }

}
