package seedu.address.model.distribute;

import org.junit.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.testutil.Assert;

public class DistributeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Distribute(0, null, null, null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidGroupName));
    }

    @Test
    public void constructor_invalidGenderFlag_throwsIllegalArgumentException() {
        String genderFlag = "2";
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIsFlagged(genderFlag));
    }

    @Test
    public void constructor_invalidNationalityFlag_throwsIllegalArgumentException() {
        String nationalityFlag = "truefalse";
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIsFlagged(nationalityFlag));
    }

}
