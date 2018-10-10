package seedu.address.model.distribute;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Distribute {

    private final int index;
    private final DistributeGroupName groupName;
    private final Boolean gender;
    private final Boolean nationality;

    /**
     * Every field must be present and not null.
     */

    public Distribute(int index, DistributeGroupName groupName, Boolean gender, Boolean nationality) {
        requireAllNonNull(index, groupName, gender, nationality);
        this.index = index;
        this.groupName = groupName;
        this.gender = gender;
        this.nationality = nationality;
    }

    public int getIndex() {
        return index;
    }

    public DistributeGroupName getGroupName() {
        return groupName;
    }

    public Boolean getGender() {
        return gender;
    }

    public Boolean getNationality() {
        return nationality;
    }

    //    /**
    //     * Returns true if both persons have the same identity and data fields.
    //     * This defines a stronger notion of equality between two persons.
    //     */
    //    @Override
    //    public boolean equals(Object other) {
    //        if (other == this) {
    //            return true;
    //        }
    //
    //        if (!(other instanceof Distribute)) {
    //            return false;
    //        }
    //
    //        Distribute otherDist = (Distribute) other;
    //        return otherDist.getGroupName().equals(getGroupName());
    //
    //    }

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
                .append(getGroupName())
                .append("Sort By Gender: ")
                .append(getGender())
                .append("Sort By Nationality: ")
                .append(getNationality());
        return builder.toString();
    }
}
