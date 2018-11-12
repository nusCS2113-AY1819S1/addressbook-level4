package seedu.address.testutil;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.workout.Parameter;

/**
 * A utility class to help with building Parameter objects.
 */
public class ParameterBuilder {

    public static final String DEFAULT_PREFIX = "name/";
    public static final String DEFAULT_NAME = "commando";

    private Prefix prefix;
    private String value;


    public ParameterBuilder() {
        prefix = new Prefix(DEFAULT_PREFIX);
        value = DEFAULT_NAME;
    }

    /**
     * Initializes the ParameterBuilder with the data of {@code parameterToCopy}.
     */
    public ParameterBuilder(Parameter parameterToCopy) {
        prefix = parameterToCopy.getPrefix();
        value = parameterToCopy.getValue();
    }

    /**
     * Sets the {@code Prefix} of the {@code Parameter} that we are building.
     */
    public ParameterBuilder withPrefix(String name) {
        this.prefix = new Prefix(name);
        return this;
    }

    /**
     * Sets the {@code value} of the {@code Parameter} that we are building.
     */

    public ParameterBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Parameter build() {
        return new Parameter(prefix, value);
    }

}
