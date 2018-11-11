package seedu.recruit.model.company;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BMW;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.ALFA;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.BMW;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.testutil.CompanyBuilder;

public class CompanyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameCompany() {
        // same object -> returns true
        assertTrue(ALFA.isSameCompany(ALFA));

        // null -> returns false
        assertFalse(ALFA.isSameCompany(null));

        // different name -> returns false
        Company editedAlfa = new CompanyBuilder(ALFA).withCompanyName(VALID_NAME_BMW).build();
        assertFalse(ALFA.isSameCompany(editedAlfa));

        // same name, different attributes -> returns true
        editedAlfa = new CompanyBuilder(ALFA).withEmail(VALID_EMAIL_BMW).withAddress(VALID_ADDRESS_BMW).build();
        assertTrue(ALFA.isSameCompany(editedAlfa));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Company aliceCopy = new CompanyBuilder(ALFA).build();
        assertTrue(ALFA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALFA.equals(ALFA));

        // null -> returns false
        assertFalse(ALFA.equals(null));

        // different type -> returns false
        assertFalse(ALFA.equals(5));

        // different candidate -> returns false
        assertFalse(ALFA.equals(BMW));

        // different name -> returns false
        Company editedAlice = new CompanyBuilder(ALFA).withCompanyName(VALID_NAME_BMW).build();
        assertFalse(ALFA.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CompanyBuilder(ALFA).withPhone(VALID_PHONE_BMW).build();
        assertFalse(ALFA.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CompanyBuilder(ALFA).withEmail(VALID_EMAIL_BMW).build();
        assertFalse(ALFA.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CompanyBuilder(ALFA).withAddress(VALID_ADDRESS_BMW).build();
        assertFalse(ALFA.equals(editedAlice));

    }
}
