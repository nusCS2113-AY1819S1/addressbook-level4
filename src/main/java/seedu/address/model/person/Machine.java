package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Machine in the lab. Morphed from Persons.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Machine {

    // Identity fields
    private final Name name;

    // Data fields
    private final List<Name> jobs = new ArrayList<>();  //Name is a placeholder. To be replaced by Job class in the future
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Machine(Name name, List<Name> jobs, Set<Tag> tags) {
        requireAllNonNull(name, jobs, tags);
        this.name = name;
        this.jobs.addAll(jobs);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable Job List, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Name> getJobs() {
        return Collections.unmodifiableList(jobs);
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Returns true if both persons of the same name and same list of Jobs.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMachine(Machine otherMachine) {
        if (otherMachine == this) {
            return true;
        }

        return otherMachine != null
                && otherMachine.getName().equals(getName())
                && otherMachine.getJobs().equals(getJobs());
    }

    /**
     * Returns true if both machines have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Machine)) {
            return false;
        }

        Machine otherMachine = (Machine) other;
        return otherMachine.getName().equals(getName())
                && otherMachine.getJobs().equals(getJobs())
                && otherMachine.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, jobs, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Jobs: ");
        getJobs().forEach(builder::append);

        return builder.toString();
    }

}
