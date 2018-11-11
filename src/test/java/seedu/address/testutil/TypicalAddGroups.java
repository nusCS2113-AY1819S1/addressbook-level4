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
 * Provides typical AddGroup objects.
 */
public class TypicalAddGroups {

    /**
     * Returns {@code addGroup} with a group index of the first group and multiple person indexes.
     *
     * @return AddGroup containing first group index and multiple person indexes.
     */
    public static AddGroup getAddGroup1() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }

    /**
     * Returns {@code addGroup} with a group index of the second group and multiple person indexes.
     *
     * @return AddGroup containing second group index and multiple person indexes.
     */
    public static AddGroup getAddGroup2() {
        AddGroup addGroup = new AddGroup(INDEX_SECOND_GROUP, getTypicalPersonIndicesSet());
        return addGroup;
    }

    /**
     * Returns {@code addGroup} with a group index of the first group and one person index.
     *
     * @return AddGroup containing first group index and one person index.
     */
    public static AddGroup getAddGroup3() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());
        return addGroup;
    }

    /**
     * Returns {@code addGroup} with group index of the first group, one person index
     * and the indexes respective group and person.
     *
     * @return AddGroup containing first group index, person index, group and person.
     */
    public static AddGroup getAddGroupWithGroupAndPerson() {
        AddGroup addGroup = new AddGroup(INDEX_FIRST_GROUP, getSingleTypicalPersonIndicesSet());

        List<Person> personList = new ArrayList<>(Arrays.asList(ALICE));
        List<Group> groupList = new ArrayList<>(Arrays.asList(getTut1()));

        addGroup.setPersonSet(personList);
        addGroup.setGroupSet(groupList);
        return addGroup;
    }

}
