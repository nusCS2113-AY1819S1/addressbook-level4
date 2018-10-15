package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.getSingleTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndicesSet;

import seedu.address.model.group.AddGroup;

public class TypicalAddGroups {

    public static AddGroup getAddGroup1(){
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }
    public static AddGroup getAddGroup2(){
        AddGroup addGroup = new AddGroup(INDEX_SECOND_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }
    public static AddGroup getAddGroup3(){
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());
        return addGroup;
    }

}
