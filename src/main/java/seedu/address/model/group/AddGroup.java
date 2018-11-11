package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Represents persons to be added to a group in the address book.
 * Guarantees: Details are present and not null, field values are validated.
 */
public class AddGroup {

    private static final String COLON_SEPARATOR = " : ";

    // Identity fields
    private final Index groupIndex;
    private final Set<Index> personIndices = new HashSet<>();

    //Data Fields
    private final Set<Group> groupSet = new HashSet<>();
    private final Set<Person> personSet = new HashSet<>();

    /**
     * Receives indexes of group and persons.
     *
     * @param groupIndex Group index.
     * @param personIndices Person indexes.
     */
    public AddGroup(Index groupIndex, Set<Index>personIndices) {
        requireAllNonNull(groupIndex, personIndices);
        this.groupIndex = groupIndex;
        this.personIndices.addAll(personIndices);
    }

    /**
     * Returns the group which persons are to be added to.
     *
     * @return First group.
     */
    public Group getGroup() {
        return groupSet.iterator().next();
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }

    /**
     * Sets the group which persons are to be added to.
     *
     * @param group List of all groups in the address book.
     */
    public void setGroupSet(List<Group> group) {
        requireNonNull(group);
        groupSet.add(group.get(groupIndex.getZeroBased()));
    }

    /**
     * Sets the persons to be added to group.
     *
     * @param person List of all persons in teh address book.
     */
    public void setPersonSet(List<Person> person) {
        requireNonNull(person);
        for (Index i : personIndices) {
            personSet.add(person.get(i.getZeroBased()));
        }
    }

    /**
     * Returns true if person index given by user is in range of what
     * is displayed on the PersonListPanel.
     *
     * @param size Number of persons in the address book.
     * @return Validation result.
     */
    public boolean validPersonIndexSet(int size) {
        for (Index i : personIndices) {
            if (i.getZeroBased() >= size) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if group index given by user is in range of what
     * is displayed on the GroupListPanel.
     *
     * @param size Number of groups in the address book.
     * @return Validation result.
     */
    public boolean validGroupIndex(int size) {
        if (groupIndex.getZeroBased() >= size) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if both objects have the same identity and data fields.
     * This defines a stronger notion of equality between two AddGroups.
     *
     * @param other AddGroup to compare with.
     * @return Comparison result.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroup // instanceof handles nulls
                && groupIndex.equals(((AddGroup) other).groupIndex) // state check
                && personIndices.equals(((AddGroup) other).personIndices)
                && personSet.equals(((AddGroup) other).personSet));
    }

    /**
     * Returns string with AddGroup identity field details.
     *
     * @return AddGroup details.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(groupIndex)
                .append(COLON_SEPARATOR);
        personIndices.forEach(builder::append);
        return builder.toString();
    }

}
