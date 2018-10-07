package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;


/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {
    // Identity fields
    private final GroupName groupName;
    private final GroupLocation groupLocation;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Group(GroupName groupName, GroupLocation groupLocation, Set<Tag> tags) {
        requireAllNonNull(groupName, groupLocation, tags);
        this.groupName = groupName;
        this.groupLocation = groupLocation;
        this.tags.addAll(tags);
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupLocation getGroupLocation() {
        return groupLocation;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }
        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName())
                && (otherGroup.getGroupLocation().equals(getGroupLocation()));
    }

    /**
     * Returns true if both group exist
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(groupName, groupLocation, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGroupName())
                .append(" Group Location: ")
                .append(getGroupLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
