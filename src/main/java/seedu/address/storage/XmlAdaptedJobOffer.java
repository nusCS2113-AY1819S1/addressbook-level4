package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.joboffer.Company;
import seedu.address.model.joboffer.Job;
import seedu.address.model.joboffer.JobOffer;

/**
 * JAXB-friendly version of the JobOffer.
 */
public class XmlAdaptedJobOffer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job offer's %s field is missing!";

    @XmlElement(required = true)
    private String job;
    @XmlElement(required = true)
    private String company;


    /**
     * Constructs an XmlAdaptedJobOffer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJobOffer() {}

    /**
     * Constructs an {@code XmlAdaptedJobOffer} with the given job offer details.
     */

    public XmlAdaptedJobOffer(String job, String company) {
        this.job = job;
        this.company = company;
    }

    /**
     * Converts a given JobOffer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJobOffer
     */

    public XmlAdaptedJobOffer(JobOffer source) {

        job = source.getJob().value;
        company = source.getCompany().value;
    }

    /**
     * Converts this jaxb-friendly adapted job offer object into the model's JobOffer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job offer
     */

    public JobOffer toModelType() throws IllegalValueException {

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }

        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_COMPANY_CONSTRAINTS);
        }

        final Company modelCompany = new Company(company);


        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }

        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_JOB_CONSTRAINTS);
        }

        final Job modelJob = new Job(job);


        return new JobOffer(modelCompany, modelJob);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJobOffer)) {
            return false;
        }

        XmlAdaptedJobOffer otherPerson = (XmlAdaptedJobOffer) other;
        return Objects.equals(company, otherPerson.company)
                && Objects.equals(job, otherPerson.job);
    }
}

