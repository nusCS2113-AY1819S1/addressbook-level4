package seedu.address.testutil;

import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.getSingleTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Provides AddGroup object stubs
 */
public class TypicalAddGroups {

    public static AddGroup getAddGroup1() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }
    public static AddGroup getAddGroup2() {
        AddGroup addGroup = new AddGroup(INDEX_SECOND_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }
    public static AddGroup getAddGroup3() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());
        return addGroup;
    }
    public static AddGroup getAddGroupWithGroupAndPerson() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());

        List<Person> personList = new ArrayList<>(Arrays.asList(ALICE));
        List<Group> groupList = new ArrayList<>(Arrays.asList(getTut1()));

        addGroup.setPersonSet(personList);
        addGroup.setGroupSet(groupList);
        return addGroup;
    }

}
