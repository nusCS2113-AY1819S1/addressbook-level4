package seedu.recruit.testutil;

import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BMW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.company.Company;



/**
 * A utility class containing a list of {@code Company} objects to be used in tests.
 */

public class TypicalCompanies {
    public static final Company AUDI = new CompanyBuilder().withCompanyName("Audi AG")
            .withAddress("281 Alexandra Rd, Singapore 159938").withEmail("customerservice@myaudiworld.sg")
            .withPhone("65133674").build();
    public static final Company BENTLEY = new CompanyBuilder().withCompanyName("Bentley Motors Limited")
            .withAddress("45 Leng Kee Rd, Singapore 159103").withEmail("customerservice@bentley.sg")
            .withPhone("63782628").build();
    public static final Company CHEVROLET = new CompanyBuilder().withCompanyName("Chevrolet Alpine Motors")
            .withAddress("7 Ubi Cl, Singapore 408604").withEmail("customerservice@chevrolet.sg")
            .withPhone("65113033").build();
    public static final Company DODGE = new CompanyBuilder().withCompanyName("Dodge Brothers Company")
            .withAddress("123 Dodge Ave, Singapore 123321").withEmail("customerservice@dodge.sg")
            .withPhone("6123321").build();

    // Manually added - Company details found in {@code CommandTestUtil}
    public static final Company ALFA = new CompanyBuilder().withCompanyName(VALID_NAME_ALFA)
            .withAddress(VALID_ADDRESS_ALFA).withEmail(VALID_EMAIL_ALFA).withPhone(VALID_PHONE_ALFA).build();
    public static final Company BMW = new CompanyBuilder().withCompanyName(VALID_NAME_BMW)
            .withAddress(VALID_ADDRESS_BMW).withEmail(VALID_EMAIL_BMW).withPhone(VALID_PHONE_BMW).build();

    private TypicalCompanies() {}

    /**
     * Returns an {@code CompanyBook} with all the typical companies.
     */
    public static CompanyBook getTypicalCompanyBook() {
        CompanyBook cb = new CompanyBook();
        for (Company company : getTypicalCompanies()) {
            cb.addCompany(company);
        }
        return cb;
    }
    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(AUDI, BENTLEY, CHEVROLET, DODGE));
    }

}
