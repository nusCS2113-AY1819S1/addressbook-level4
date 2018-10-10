package seedu.address.storage;

import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.testutil.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedDistributor.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDistributors.AHB;

public class XmlAdaptedDistributorTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";


    private static final String VALID_NAME = AHB.getDistName().toString();
    private static final String VALID_PHONE = AHB.getDistPhone().toString();


    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedDistributor distributor = new XmlAdaptedDistributor(AHB);
        assertEquals(AHB, distributor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor =
                new XmlAdaptedDistributor(INVALID_NAME, VALID_PHONE);
        String expectedMessage = DistributorName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor = new XmlAdaptedPerson(null, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DistributorName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE);
        String expectedMessage = DistributorPhone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedDistributor distributor = new XmlAdaptedPerson(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DistributorPhone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, distributor::toModelType);
    }


}
