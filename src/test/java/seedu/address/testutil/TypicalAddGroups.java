package seedu.address.testutil;

import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersonIndexs.getSingleTypicalPersonIndexs;
import static seedu.address.testutil.TypicalPersonIndexs.getTypicalPersonIndexs;

import seedu.address.model.group.AddGroup;

public class TypicalAddGroups {
    public static final AddGroup ADD_GROUP_1 = new AddGroup(TUT_1.getGroupName(),getTypicalPersonIndexs());
    public static final AddGroup ADD_GROUP_2 = new AddGroup(CS1010.getGroupName(),getTypicalPersonIndexs());
    public static final AddGroup ADD_GROUP_3 = new AddGroup(TUT_1.getGroupName(),getSingleTypicalPersonIndexs());
}
