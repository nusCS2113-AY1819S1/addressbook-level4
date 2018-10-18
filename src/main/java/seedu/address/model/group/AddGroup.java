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
 * Guarantees: details are present and not null, field values are validated, immutable.
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
     * Every field must be present and not null.
     */
    public AddGroup(Index groupIndex, Set<Index>personIndices) {
        requireAllNonNull(groupIndex, personIndices);
        this.groupIndex = groupIndex;
        this.personIndices.addAll(personIndices);
    }

    public Group getGroup() {
        return groupSet.iterator().next();
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setGroupSet(List<Group> group) {
        requireNonNull(group);
        groupSet.add(group.get(groupIndex.getZeroBased()));
    }

    public void setPersonSet(List<Person> person) {
        requireNonNull(person);
        for (Index i : personIndices) {
            personSet.add(person.get(i.getZeroBased()));
        }
    }

    /**
     * Check if person index input by user is in range of what
     * is displayed on the person list panel.
     * @param size
     * @return
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
     * Check if group index input by user is in range of what
     * is displayed on the group list panel.
     * @param size
     * @return
     */
    public boolean validGroupIndex(int size) {
        if (groupIndex.getZeroBased() >= size) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroup // instanceof handles nulls
                && groupIndex.equals(((AddGroup) other).groupIndex) // state check
                && personIndices.equals(((AddGroup) other).personIndices)
                && personSet.equals(((AddGroup) other).personSet));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(groupIndex)
                .append(COLON_SEPARATOR);
        personIndices.forEach(builder::append);
        return builder.toString();
    }

}
