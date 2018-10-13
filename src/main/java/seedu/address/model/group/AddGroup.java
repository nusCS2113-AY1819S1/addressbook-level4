package seedu.address.model.group;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.PersonIndex;

/**
 * Represents persons to be added to a group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class AddGroup {
    // Identity fields
    private final GroupName groupName;

    //Data Fields
    private final Set<PersonIndex> personIndexs = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public AddGroup(GroupName groupName, Set<PersonIndex>personIndexs){
        this.groupName = groupName;
        this.personIndexs.addAll(personIndexs);
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public Set<PersonIndex> getPersonIndexes(){
        return Collections.unmodifiableSet(personIndexs);
    }

    // TODO
    // test
    public boolean validPersonIndexsSet(int size) {
        for (PersonIndex i : personIndexs){
            if(Integer.parseInt(i.personIndex) > size || Integer.parseInt(i.personIndex) <= 0){
                return false;
            }
        }
        return true;
    }

    // TODO
    // test
    public boolean validGroupName(List<Group> lastShownGroupList) {
        for (Group i : lastShownGroupList){
            if(groupName.equals(i.getGroupName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroup // instanceof handles nulls
                && groupName.equals(((AddGroup) other).groupName)
                && personIndexs.equals(((AddGroup) other).personIndexs)); // state check
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(groupName)
                .append(" : ");
        personIndexs.forEach(builder::append);
        return builder.toString();
    }

}
