package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.drink.Batch;
import seedu.address.model.drink.BatchDate;
import seedu.address.model.drink.BatchId;
import seedu.address.model.drink.BatchQuantity;

/**
 * JAXB-friendly adapted version of the Drink batch.
 */
public class XmlAdaptedBatch {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Batch's %s field is missing!";

    @XmlElement(required = true)
    private String batchId;

    @XmlElement(required = true)
    private String batchQuantity;

    @XmlElement(required = true)
    private String batchDate;

    /**
     * Constructs an XmlAdaptedBatch.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBatch() {}

    /**
     * Constructs a {@code XmlAdaptedBatch} with the given fields.
     */
    public XmlAdaptedBatch(BatchId batchIdInput, BatchQuantity batchQuantityInput, BatchDate batchDateInput) {
        this.batchId = batchIdInput.toString();
        this.batchQuantity = batchQuantityInput.toString();
        this.batchDate = batchDateInput.toString();
    }
    /**
     * Converts a given Batch into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBatch
     */
    public XmlAdaptedBatch(Batch source) {
        batchId = source.getBatchId().toString();
        batchQuantity = source.getBatchQuantity().toString();
        batchDate = source.getBatchDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted batch object into the model's Batch object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Batch toModelType() throws IllegalValueException {
        if (batchId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, BatchId.class.getSimpleName()));
        }
        if (!BatchId.isValidBatchId(batchId)) {
            throw new IllegalValueException(BatchId.MESSAGE_BATCH_ID_CONSTRAINTS);
        }
        final BatchId modelBatchId = new BatchId(batchId);

        if (batchQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BatchQuantity.class.getSimpleName()));
        }
        if (!BatchQuantity.isValidQuantity(batchQuantity)) {
            throw new IllegalValueException(BatchQuantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final BatchQuantity modelBatchQuantity = new BatchQuantity(batchQuantity);

        if (batchDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BatchDate.class.getSimpleName()));
        }
        if (!BatchDate.isValidDate(batchDate)) {
            throw new IllegalValueException(BatchDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final BatchDate modelDate = new BatchDate(batchDate);
        return new Batch(modelBatchId, modelBatchQuantity, modelDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedBatch)) {
            return false;
        }
        return batchId.equals(((XmlAdaptedBatch) other).batchId);
    }
}
