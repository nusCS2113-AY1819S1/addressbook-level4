package seedu.recruit.testutil;

import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.UniqueJobList;

/**
 * A utility class to help with building Company objects.
 */

public class CompanyBuilder {

    public static final String DEFAULT_COMPANY_NAME = "Mercedes Pte Ltd";
    public static final String DEFAULT_COMPANY_ADDRESS = "123 Sunny Lane #01-11 600611";
    public static final String DEFAULT_COMPANY_EMAIL = "mercedes@mail.com";
    public static final String DEFAULT_COMPANY_PHONE = "61236321";


    private CompanyName companyName;
    private Address address;
    private Email email;
    private Phone phone;
    private UniqueJobList jobList;

    public CompanyBuilder() {
        companyName = new CompanyName(DEFAULT_COMPANY_NAME);
        address = new Address(DEFAULT_COMPANY_ADDRESS);
        email = new Email(DEFAULT_COMPANY_EMAIL);
        phone = new Phone(DEFAULT_COMPANY_PHONE);
        jobList = new UniqueJobList();
    }

    /**
     * Initializes the CompanyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        companyName = companyToCopy.getCompanyName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        jobList = companyToCopy.getUniqueJobList();
    }

    /**
     * Sets the {@code CompanyName} of the {@code Company} that we are building.
     */

    public CompanyBuilder withCompanyName(String companyName) {
        this.companyName = new CompanyName(companyName);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Company} that we are building.
     */

    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Company} that we are building.
     */

    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Company} that we are building.
     */

    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code jobList} of the {@code Company} that we are building.
     */

    public CompanyBuilder withJobList(UniqueJobList jobList) {
        this.jobList = jobList;
        return this;
    }

    public Company build() {
        return new Company(companyName, address, email, phone, jobList);
    }
}
