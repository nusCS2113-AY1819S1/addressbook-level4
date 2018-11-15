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
    private final Set<DistributorProduct> distprods = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    private final DistributorPhone defaultdistphone = new DistributorPhone("00000000");

    /**
     * Only name must be present and not null.
     */
    public Distributor(DistributorName distname, DistributorPhone distphone,
                       Set<DistributorProduct> distprods, Set<Tag> tags) {
        requireAllNonNull(distname);
        this.distname = distname;
        this.distphone = distphone;
        this.distprods.addAll(distprods);
        this.tags.addAll(tags);
    }


    public DistributorName getDistName() {
        return distname;
    }

    public DistributorPhone getDistPhone() {
        return distphone;
    }

    public Set<DistributorProduct> getDistProds() {
        return distprods; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both distirubtors have the same name.
     * This defines a weaker notion of equality between two distributors.
     */
    public boolean isSameDistributor(seedu.address.model.distributor.Distributor otherDistributor) {

        if (this.isSameDistributorName(otherDistributor) && this.isSameDistributorPhone(otherDistributor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if both distributors have the same name.
     * This defines a weaker notion of equality between two distributors.
     */
    public boolean isSameDistributorName(seedu.address.model.distributor.Distributor otherDistributor) {

        if (otherDistributor.getDistName() == this.getDistName()) {
            return true;
        }

        return otherDistributor != null
                && otherDistributor.getDistName().equals(getDistName());
    }

    /**
     * Returns true if both distributors have the same phone, and the phone numbers are not the default 00000000.
     * This defines a weaker notion of equality between two distributors.
     */
    public boolean isSameDistributorPhone(seedu.address.model.distributor.Distributor otherDistributor) {

        if (otherDistributor.getDistPhone() == this.getDistPhone()) {
            return true;
        }

        return otherDistributor != null
                && otherDistributor.getDistPhone().equals(getDistPhone());
    }

    /**
     * Returns true if both distributors have the same identity and data fields.
     * This defines a stronger notion of equality between two distributors.
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
                && otherDistributor.getDistProds().equals(getDistProds())
                && otherDistributor.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(distname, distphone, distprods, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDistName())
                .append("\nPhone: ")
                .append(getDistPhone())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        builder.append("\nProducts: ");
        getDistProds().forEach(builder::append);
        return builder.toString();
    }

}
