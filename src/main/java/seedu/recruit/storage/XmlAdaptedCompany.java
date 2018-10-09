package seedu.recruit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.UniqueJobList;

/**
 * JAXB-friendly version of the Company.
 */

public class XmlAdaptedCompany {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Company's %s field is missing!";

    @XmlElement(required = true)
    private String companyName;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private List<XmlAdaptedJobOffer> jobOffer;

    /**
     * Constructs an XmlAdaptedCompany.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCompany() {}

    /**
     * Constructs an {@code XmlAdaptedCompany} with the given job offer details.
     */

    public XmlAdaptedCompany(String companyName, String address, String email, String phone,
                             List<XmlAdaptedJobOffer> jobOffer) {
        this.companyName = companyName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.jobOffer = jobOffer;

    }

    /**
     * Converts a given Company into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCompany
     */

    public XmlAdaptedCompany(Company source) {
        companyName = source.getCompanyName().value;
        address = source.getAddress().value;
        email = source.getEmail().value;
        phone = source.getPhone().value;
        jobOffer = source.getJobOffers().stream().map(XmlAdaptedJobOffer::new).collect(Collectors.toList());

    }

    /**
     * Converts this jaxb-friendly adapted job offer object into the model's Company object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job offer
     */

    public Company toModelType() throws IllegalValueException {
        final List<JobOffer> companyJobOffers = new ArrayList<>();
        if (jobOffer != null) {
            for (XmlAdaptedJobOffer jobOffer : jobOffer) {
                companyJobOffers.add(jobOffer.toModelType());
            }
        }

        if (companyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CompanyName.class.getSimpleName()));
        }

        if (!CompanyName.isValidCompanyName(companyName)) {
            throw new IllegalValueException(CompanyName.MESSAGE_COMPANY_CONSTRAINTS);
        }

        final CompanyName modelCompanyName = new CompanyName(companyName);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final UniqueJobList modelJobOffers = new UniqueJobList();

        modelJobOffers.setJobOffers(companyJobOffers);


        return new Company (modelCompanyName, modelAddress, modelEmail, modelPhone, modelJobOffers);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCompany)) {
            return false;
        }

        XmlAdaptedCompany otherCompany = (XmlAdaptedCompany) other;
        return Objects.equals(companyName, otherCompany.companyName)
                && Objects.equals(address, otherCompany.address);
    }

}
