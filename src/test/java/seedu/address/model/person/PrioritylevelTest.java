package seedu.address.model.person;

import org.junit.Test;

import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.testutil.Assert;

//@@author jylee-git
public class PrioritylevelTest {

    @Test
    public void constructor_invalidArgument_throwsIllegalValueException() {
        //Priority levels out of range
        Assert.assertThrows(IllegalArgumentException.class, () -> new PriorityLevel(
                PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode() - 1));
        Assert.assertThrows(IllegalArgumentException.class, () -> new PriorityLevel(
                PriorityLevelEnum.BASIC.getPriorityLevelCode() + 1));
    }

}
