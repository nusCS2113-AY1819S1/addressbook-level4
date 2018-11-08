package seedu.address.model.distribute;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DistributeAlgorithmTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DistributeAlgorithm(null, null));
    }
}
