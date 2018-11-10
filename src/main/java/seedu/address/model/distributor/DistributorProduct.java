package seedu.address.model.distributor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product that a Distributor provides.
 * Guarantees: immutable; name is valid as declared in {@link #isValidName(String)}
 */
public class DistributorProduct {

    public static final String MESSAGE_DISTPROD_CONSTRAINTS = "Product names belonging to a distributor should only "
            + "contain alphanumeric characters and spaces, and it should not be blank";
    public static final String DISTPRODNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String distributorProducts;

    /**
     * Constructs a set {@code DistributorProduct}.
     *
     * @param distributorProducts A valid product name.
     */
    public DistributorProduct(String distributorProducts) {
        requireNonNull(distributorProducts);
        checkArgument(isValidName(distributorProducts), MESSAGE_DISTPROD_CONSTRAINTS);
        this.distributorProducts = distributorProducts;
    }

    /**
     * Returns true if a given string is a valid product name.
     */
    public static boolean isValidName(String test) {
        return test.matches(DISTPRODNAME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DistributorProduct // instanceof handles nulls
                && distributorProducts.equals(((DistributorProduct) other).distributorProducts)); // state check
    }

    @Override
    public int hashCode() {
        return distributorProducts.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "\n" + distributorProducts;
    }

}
