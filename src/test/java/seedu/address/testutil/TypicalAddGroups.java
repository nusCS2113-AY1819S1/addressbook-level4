package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.getSingleTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndicesSet;

import seedu.address.model.group.AddGroup;

public class TypicalAddGroups {
    public static final AddGroup ADD_GROUP_1 = new AddGroup(INDEX_FIRST_GROUP, getTypicalPersonIndicesSet());
    public static final AddGroup ADD_GROUP_2 = new AddGroup(INDEX_SECOND_GROUP, getTypicalPersonIndicesSet());
    public static final AddGroup ADD_GROUP_3 = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());
}
