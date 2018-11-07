package seedu.address.model.distributor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Distributor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Distributor {

    // Identity fields
    private final DistributorName distname;
    private final DistributorPhone distphone;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Only name must be present and not null.
     */
    public Distributor(DistributorName distname, DistributorPhone distphone, Set<Tag> tags) {
        requireAllNonNull(distname);
        this.distname = distname;
        this.distphone = distphone;
        this.tags.addAll(tags);
    }

    public DistributorName getDistName() {
        return distname;
    }

    public DistributorPhone getDistPhone() {
        return distphone;
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
    public boolean isSameDistributor(seedu.address.model.distributor.Distributor otherDistributor) {
        if (otherDistributor == this) {
            return true;
        }

        return otherDistributor != null
                && otherDistributor.getDistPhone().equals(getDistPhone())
                || otherDistributor.getDistName().equals(getDistName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.distributor.Distributor)) {
            return false;
        }

        seedu.address.model.distributor.Distributor otherDistributor =
                (seedu.address.model.distributor.Distributor) other;
        return otherDistributor.getDistName().equals(getDistName())
                && otherDistributor.getDistPhone().equals(getDistPhone())
                && otherDistributor.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(distname, distphone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDistName())
                .append(" Phone: ")
                .append(getDistPhone())
                .append(tags);
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
