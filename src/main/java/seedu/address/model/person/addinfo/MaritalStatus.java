package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Values for marital status
 */
enum MaritalStatusEnum {
    MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorced");

    private String maritalStatus;

    MaritalStatusEnum(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }
}

/**
 * Represents a person's marital status in Health Book.
 */
public class MaritalStatus {
    public static final String MARITAL_VALIDATION_REGEX = "[MSD]";
    public static final String MESSAGE_MARITAL_CONSTRAINT = "Marital status input can only be either S (for single),"
            + " M (for married) or D (for divorced).";

    public final Optional<MaritalStatusEnum> value;

    private final String emptyString = "";

    public MaritalStatus(String maritalStatus) {
        requireNonNull(maritalStatus);

        switch(maritalStatus) {
        case "M":
        case "Married":
            this.value = Optional.of(MaritalStatusEnum.MARRIED);
            break;
        case "S":
        case "Single":
            this.value = Optional.of(MaritalStatusEnum.SINGLE);
            break;
        case "D":
        case "Divorced":
            this.value = Optional.of(MaritalStatusEnum.DIVORCED);
            break;
        default:
            this.value = Optional.empty();
            break;
        }
    }

    public static boolean isValidMaritalStatus(String test) {
        return test.matches(MARITAL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value.isPresent()) {
            return value.get().getMaritalStatus();
        } else {
            return emptyString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MaritalStatus
                && value.equals(((MaritalStatus) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
