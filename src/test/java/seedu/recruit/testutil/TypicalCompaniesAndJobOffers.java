package seedu.recruit.testutil;

import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BMW;
import static seedu.recruit.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * A utility class containing a list of {@code Company} and a list of {@code Job Offer} objects to be used in tests.
 */

public class TypicalCompaniesAndJobOffers {

    // ============================== COMPANY ================================================ //

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
            .withPhone("61233211").build();

    // Manually added - Company details found in {@code CommandTestUtil}
    public static final Company ALFA = new CompanyBuilder().withCompanyName(VALID_NAME_ALFA)
            .withAddress(VALID_ADDRESS_ALFA).withEmail(VALID_EMAIL_ALFA).withPhone(VALID_PHONE_ALFA).build();
    public static final Company BMW = new CompanyBuilder().withCompanyName(VALID_NAME_BMW)
            .withAddress(VALID_ADDRESS_BMW).withEmail(VALID_EMAIL_BMW).withPhone(VALID_PHONE_BMW).build();

    public static final String KEYWORD_MATCHING_AUDI = "Audi"; // A keyword that matches AUDI
    public static final String KEYWORD_MATCHING_WHEELER = "Wheeler"; // A keyword that matches WHEELER

    // ============================== JOB OFFER ================================================ //

    public static final JobOffer CASHIER_AUDI = new JobOfferBuilder().withCompanyName("Audi AG")
            .withAgeRange("20-50").withEducation("OLEVELS").withGender("F").withJob("Cashier")
            .withSalary("2000").withCandidateList(ALICE).build();

    public static final JobOffer CASHIER_BENTLEY = new JobOfferBuilder().withCompanyName("Bentley Motors Limited")
            .withAgeRange("20-50").withEducation("OLEVELS").withGender("F").withJob("Cashier")
            .withSalary("1900").withCandidateList(ALICE).build();

    public static final JobOffer CASHIER_CHEVROLET = new JobOfferBuilder().withCompanyName("Chevrolet Alpine Motors")
            .withAgeRange("20-50").withEducation("OLEVELS").withGender("F").withJob("Cashier")
            .withSalary("1500").withCandidateList(ALICE).build();

    public static final JobOffer CASHIER_DODGE = new JobOfferBuilder().withCompanyName("Dodge Brothers Company")
            .withAgeRange("20-50").withEducation("OLEVELS").withGender("F").withJob("Cashier")
            .withSalary("2200").withCandidateList(ALICE).build();

    public static final JobOffer SALESPERSON_AUDI = new JobOfferBuilder().withCompanyName("Audi AG")
            .withAgeRange("20-35").withEducation("ALEVELS").withGender("M").withJob("Salesperson")
            .withSalary("2500").withCandidateList(ALICE).build();

    public static final JobOffer SALESPERSON_BENTLEY = new JobOfferBuilder().withCompanyName("Bentley Motors Limited")
            .withAgeRange("20-35").withEducation("ALEVELS").withGender("F").withJob("Salesperson")
            .withSalary("3000").withCandidateList(ALICE).build();

    public static final JobOffer SALESPERSON_CHEVROLET = new JobOfferBuilder().withCompanyName("Chevrolet Alpine Motors")
            .withAgeRange("20-35").withEducation("ALEVELS").withGender("F").withJob("Salesperson")
            .withSalary("2400").withCandidateList(ALICE).build();

    public static final JobOffer SALESPERSON_DODGE = new JobOfferBuilder().withCompanyName("Dodge Brothers Company")
            .withAgeRange("20-35").withEducation("ALEVELS").withGender("M").withJob("Salesperson")
            .withSalary("2800").withCandidateList(ALICE).build();

    public static final JobOffer MANAGER_AUDI = new JobOfferBuilder().withCompanyName("Audi AG")
            .withAgeRange("25-45").withEducation("BACHELOR").withGender("F").withJob("Manager")
            .withSalary("4000").withCandidateList(ALICE).build();

    public static final JobOffer MANAGER_BENTLEY = new JobOfferBuilder().withCompanyName("Bentley Motors Limited")
            .withAgeRange("25-45").withEducation("BACHELOR").withGender("F").withJob("Manager")
            .withSalary("4500").withCandidateList(ALICE).build();

    public static final JobOffer MANAGER_CHEVROLET = new JobOfferBuilder().withCompanyName("Chevrolet Alpine Motors")
            .withAgeRange("25-45").withEducation("BACHELOR").withGender("M").withJob("Manager")
            .withSalary("5000").withCandidateList(ALICE).build();

    public static final JobOffer MANAGER_DODGE = new JobOfferBuilder().withCompanyName("Dodge Brothers Company")
            .withAgeRange("25-45").withEducation("BACHELOR").withGender("M").withJob("Manager")
            .withSalary("5500").withCandidateList(ALICE).build();

    public static final String KEYWORD_MATCHING_CASHIER = "Cashier"; // A keyword that matches AUDI

    private TypicalCompaniesAndJobOffers() {}

    /**
     * Returns an {@code CompanyBook} with all the typical companies and all the typical job offers.
     */
    public static CompanyBook getTypicalCompanyBook() {
        CompanyBook companyBook = new CompanyBook();
        for (Company company : getTypicalCompanies()) {
            companyBook.addCompany(company);
        }
        for (JobOffer jobOffer : getTypicalJobOffers()) {
            companyBook.addJobOffer(jobOffer);
        }
        return companyBook;
    }

    /**
     * Returns an {@code CompanyBook} with all the typical companies and typical unique job offers.
     */
    public static CompanyBook getTypicalCompanyBookWithUniqueJobOffers() {
        CompanyBook companyBook = new CompanyBook();
        for (Company company : getTypicalCompanies()) {
            companyBook.addCompany(company);
        }
        for (JobOffer jobOffer : getTypicalUniqueJobOffers()) {
            companyBook.addJobOffer(jobOffer);
        }
        return companyBook;
    }

    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(AUDI, BENTLEY, CHEVROLET, DODGE));
    }

    public static List<JobOffer> getTypicalJobOffers() {
        return new ArrayList<>(Arrays.asList(CASHIER_AUDI, CASHIER_BENTLEY, CASHIER_CHEVROLET, CASHIER_DODGE,
                SALESPERSON_AUDI, SALESPERSON_BENTLEY, SALESPERSON_CHEVROLET, SALESPERSON_DODGE,
                MANAGER_AUDI, MANAGER_BENTLEY, MANAGER_CHEVROLET, MANAGER_DODGE));
    }

    public static List<JobOffer> getTypicalUniqueJobOffers() {
        return new ArrayList<>(Arrays.asList(SALESPERSON_AUDI, CASHIER_BENTLEY, MANAGER_DODGE));
    }

    /**
     * Returns an {@code CompanyBook} with all the typical companies in a reversed order.
     */
    public static CompanyBook getReversedCompanyBook() {
        CompanyBook cb = new CompanyBook();
        for (Company company : getReversedCompanies()) {
            cb.addCompany(company);
        }
        return cb;
    }

    public static List<Company> getReversedCompanies() {
        return new ArrayList<>(Arrays.asList(DODGE, CHEVROLET, BENTLEY, AUDI));
    }

}
