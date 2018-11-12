package seedu.address.model.workout;

import seedu.address.logic.parser.Prefix;

/**
 * Represents a Parameter in the tracked data list.
 */
public class Parameter {

    private final Prefix prefix;
    private final String value;

    public Parameter(Prefix prefix, String value) {
        this.prefix = prefix;
        this.value = value;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Parameter)) {
            return false;
        }

        Parameter otherParameter = (Parameter) other;
        return otherParameter.getPrefix().equals(getPrefix())
                && otherParameter.getValue().equals(getValue());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Prefix: ")
                .append(getPrefix())
                .append(" Value: ")
                .append(getValue());
        return builder.toString();
    }
}
