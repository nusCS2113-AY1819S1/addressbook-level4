package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    private static final String AT_LOCATION = " at location ";
    private static final String WITH_TAGS = " with tags: ";

    // Identity fields
    private final GroupName groupName;
    private final GroupLocation groupLocation;
    private final Set<Tag> tags = new HashSet<>();

    //Data Fields
    private final Set<Person> persons = new HashSet<>();

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
     * Returns an immutable person set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getPersons(){
        return Collections.unmodifiableSet(persons);
    }

    public void addPersons(Person persons){
        requireNonNull(persons);
        this.persons.add(persons);
    }

    public void addPersons(Set<Person> persons){
        requireNonNull(persons);
        this.persons.addAll(persons);
    }

    /**
     * Returns true if both groups of the same name, location and tags.
     * This defines a weaker notion of equality between two groups.
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
     * This defines a stronger notion of equality between two persons.
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
