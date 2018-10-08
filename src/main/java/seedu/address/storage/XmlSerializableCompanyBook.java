package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CompanyBook;
import seedu.address.model.ReadOnlyCompanyBook;
import seedu.address.model.company.Company;

/**
 * An Immutable CompanyBook that is serializable to XML format
 */
@XmlRootElement(name = "companybook")
public class XmlSerializableCompanyBook {

    public static final String MESSAGE_DUPLICATE_COMPANY = "Company list contains duplicate company(s).";

    @XmlElement
    private List<XmlAdaptedCompany> companies;

    /**
     * Creates an empty XmlSerializableCompanyBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCompanyBook() {
        companies = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCompanyBook(ReadOnlyCompanyBook src) {
        this();
        companies.addAll(src.getCompanyList().stream().map(XmlAdaptedCompany::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jobbook into the model's {@code CompanyBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCompany}.
     */
    public CompanyBook toModelType() throws IllegalValueException {
        CompanyBook jobBook = new CompanyBook();
        for (XmlAdaptedCompany p : companies) {
            Company job = p.toModelType();
            if (jobBook.hasCompany(job)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPANY);
            }
            jobBook.addCompany(job);
        }
        return jobBook;
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

