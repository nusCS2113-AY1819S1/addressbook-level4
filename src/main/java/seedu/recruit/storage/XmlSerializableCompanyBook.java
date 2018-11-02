package seedu.recruit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * An Immutable CompanyBook that is serializable to XML format
 */
@XmlRootElement(name = "companybook")
public class XmlSerializableCompanyBook {

    public static final String MESSAGE_DUPLICATE_COMPANY = "Company list contains duplicate company(s).";
    public static final String MESSAGE_DUPLICATE_JOB_OFFERS = "Job list contains duplicate job offer(s).";

    @XmlElement
    private List<XmlAdaptedCompany> companies;
    @XmlElement(required = true)
    private List<XmlAdaptedJobOffer> jobList;

    /**
     * Creates an empty XmlSerializableCompanyBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCompanyBook() {
        companies = new ArrayList<>();
        jobList = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCompanyBook(ReadOnlyCompanyBook src) {
        this();
        companies.addAll(src.getCompanyList().stream().map(XmlAdaptedCompany::new).collect(Collectors.toList()));
        jobList.addAll(src.getCompanyJobList().stream().map(XmlAdaptedJobOffer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this CompanyBook into the model's {@code CompanyBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the Company Book
     * {@code XmlAdaptedCompany}.
     */
    public CompanyBook toModelType() throws IllegalValueException {
        CompanyBook companyBook = new CompanyBook();
        for (XmlAdaptedCompany p : companies) {
            Company company = p.toModelType();
            if (companyBook.hasCompany(company)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPANY);
            }
            companyBook.addCompany(company);
        }
        for (XmlAdaptedJobOffer j : jobList) {
            JobOffer jobOffer = j.toModelType();
            if (companyBook.hasJobOffer(jobOffer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JOB_OFFERS);
            }
            companyBook.addJobOffer(jobOffer);
        }
        return companyBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCompanyBook)) {
            return false;
        }
        return companies.equals(((XmlSerializableCompanyBook) other).companies);
    }
}

