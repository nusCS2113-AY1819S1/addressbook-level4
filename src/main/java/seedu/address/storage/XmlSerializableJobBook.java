package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.JobBook;
import seedu.address.model.ReadOnlyJobBook;
import seedu.address.model.joboffer.JobOffer;

/**
 * An Immutable JobBook that is serializable to XML format
 */
@XmlRootElement(name = "jobbook")
public class XmlSerializableJobBook {

    public static final String MESSAGE_DUPLICATE_JOB_OFFER = "Jobs list contains duplicate job offer(s).";

    @XmlElement
    private List<XmlAdaptedJobOffer> jobOffers;

    /**
     * Creates an empty XmlSerializableJobBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableJobBook() {
        jobOffers = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableJobBook(ReadOnlyJobBook src) {
        this();
        jobOffers.addAll(src.getJobOfferList().stream().map(XmlAdaptedJobOffer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jobbook into the model's {@code JobBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedJobOffer}.
     */
    public JobBook toModelType() throws IllegalValueException {
        JobBook jobBook = new JobBook();
        for (XmlAdaptedJobOffer p : jobOffers) {
            JobOffer job = p.toModelType();
            if (jobBook.hasJobOffer(job)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JOB_OFFER);
            }
            jobBook.addJobOffer(job);
        }
        return jobBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableJobBook)) {
            return false;
        }
        return jobOffers.equals(((XmlSerializableJobBook) other).jobOffers);
    }
}

