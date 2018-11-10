//@@author rajdeepsh

package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a group in the address book.
 * Guarantees: Details are present and not null, field values are validated.
 */
public class Group {

    private static final String AT_LOCATION = " at location ";
    private static final String WITH_TAGS = " with tags: ";

    // Identity fields
    private final GroupName groupName;
    private final GroupLocation groupLocation;
    private final Set<Tag> tags = new HashSet<>();

    // Data Fields
    private final Set<Person> persons = new HashSet<>();

    /**
     * Receives group details.
     *
     * @param groupName Group name.
     * @param groupLocation Group Location.
     * @param tags Group tags.
     */
    public Group(GroupName groupName, GroupLocation groupLocation, Set<Tag> tags) {
        requireAllNonNull(groupName, groupLocation, tags);
        this.groupName = groupName;
        this.groupLocation = groupLocation;
        this.tags.addAll(tags);
    }

    /**
     * Returns group name.
     *
     * @return Group name.
     */
    public GroupName getGroupName() {
        return groupName;
    }

    /**
     * Returns group location.
     *
     * @return Group location.
     */
    public GroupLocation getGroupLocation() {
        return groupLocation;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return Tag Set.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable person set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return Person Set.
     */
    public Set<Person> getPersons() {
        return Collections.unmodifiableSet(persons);
    }

    /**
     * Adds a person to the group.
     *
     * @param persons Person to add.
     */
    public void addPersons(Person persons) {
        requireNonNull(persons);
        this.persons.add(persons);
    }

    /**
     * Adds multiple persons to the group.
     *
     * @param persons Persons to add.
     */
    public void addPersons(Set<Person> persons) {
        requireNonNull(persons);
        this.persons.addAll(persons);
    }

    /**
     * Returns true if both groups of the same name, location and tags.
     * This defines a weaker notion of equality between two groups.
     *
     * @param otherGroup Group to compare with.
     * @return Comparison result.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }
        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName())
                && otherGroup.getGroupLocation().equals(getGroupLocation())
                && otherGroup.getTags().equals(getTags());
    }

    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two groups.
     *
     * @param other Group to compare with.
     * @return Comparison result.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getGroupName().equals(getGroupName())
                && otherGroup.getGroupLocation().equals(getGroupLocation())
                && otherGroup.getTags().equals(getTags())
                && otherGroup.getPersons().equals(getPersons());
    }

    /**
     * Returns string with group identity field details.
     *
     * @return Group details.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(groupName)
                .append(AT_LOCATION)
                .append(groupLocation)
                .append(WITH_TAGS);
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
