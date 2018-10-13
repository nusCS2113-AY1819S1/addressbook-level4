package seedu.address.model.distribute;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.group.GroupName;

/**
 * Represent the auto-distribution process of students in the addressbook.
 */
public class Distribute {

    private final int index;
    private final GroupName groupName;
    private final Boolean gender;
    private final Boolean nationality;

    /**
     * Every field must be present and not null.
     */

    public Distribute(int index, GroupName groupName, Boolean gender, Boolean nationality) {
        requireAllNonNull(index, groupName, gender, nationality);
        this.index = index;
        this.groupName = groupName;
        this.gender = gender;
        this.nationality = nationality;
    }

    public int getIndex() {
        return index;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public Boolean getGender() {
        return gender;
    }

    public Boolean getNationality() {
        return nationality;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(index, groupName, gender, nationality);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getIndex())
                .append("Group Name: ")
                .append(getGroupName().toString())
                .append("Sort By Gender: ")
                .append(getGender())
                .append("Sort By Nationality: ")
                .append(getNationality());
        return builder.toString();
    }
}
