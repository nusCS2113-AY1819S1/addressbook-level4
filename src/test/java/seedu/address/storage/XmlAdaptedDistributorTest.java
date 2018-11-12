package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedDistributor.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDistributors.AHHUAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.testutil.Assert;

public class XmlAdaptedDistributorTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+!651234";
    private static final String INVALID_DIST_PROD = "l!pt0nz";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = AHHUAT.getDistName().toString();
    private static final String VALID_PHONE = AHHUAT.getDistPhone().toString();
    private static final List<XmlAdaptedDistProd> VALID_DIST_PROD = AHHUAT.getDistProds().stream()
            .map(XmlAdaptedDistProd::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedTag> VALID_TAGS = AHHUAT.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validDistributorDetails_returnsDistributor() throws Exception {
        XmlAdaptedDistributor distributor = new XmlAdaptedDistributor(AHHUAT);
        assertEquals(AHHUAT, distributor.toModelType());
    }

    @Test
    public void toModelType_invalidDistName_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor =
                new XmlAdaptedDistributor(INVALID_NAME, VALID_PHONE, VALID_DIST_PROD, VALID_TAGS);
        String expectedMessage = DistributorName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_nullDistName_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor = new XmlAdaptedDistributor(null, VALID_PHONE,
                VALID_DIST_PROD, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DistributorName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor =
                new XmlAdaptedDistributor(VALID_NAME, INVALID_PHONE, VALID_DIST_PROD, VALID_TAGS);
        String expectedMessage = DistributorPhone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor = new XmlAdaptedDistributor(VALID_NAME, null, VALID_DIST_PROD,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DistributorPhone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        List<XmlAdaptedDistProd> invalidDistProds = new ArrayList<>(VALID_DIST_PROD);
        invalidDistProds.add(new XmlAdaptedDistProd(INVALID_DIST_PROD));
        XmlAdaptedDistributor distributor =
                new XmlAdaptedDistributor(VALID_NAME, VALID_PHONE, invalidDistProds, VALID_TAGS);
        String expectedMessage = DistributorProduct.MESSAGE_DISTPROD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedDistributor distributor =
                new XmlAdaptedDistributor(VALID_NAME, VALID_PHONE, VALID_DIST_PROD, invalidTags);
        Assert.assertThrows(IllegalValueException.class, distributor::toModelType);
    }
}
