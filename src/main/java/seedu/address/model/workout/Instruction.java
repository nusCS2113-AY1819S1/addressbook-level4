package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Instruction in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstruction(String)}
 */
public class Instruction {

    public static final String MESSAGE_INSTRUCTION_CONSTRAINTS =
            "Instruction should only contain alphanumeric characters , punctuation, and spaces,"
                    + "and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INSTRUCTION_VALIDATION_REGEX = "[\\p{Alnum}|\\p{Punct}][\\p{Alnum} |\\p{Punct} ]*";

    public final String fullInstruction;

    /**
     * Constructs a {@code Instruction}.
     *
     * @param instruction A valid instruction.
     */
    public Instruction(String instruction) {
        requireNonNull(instruction);
        checkArgument(isValidInstruction(instruction), MESSAGE_INSTRUCTION_CONSTRAINTS);
        fullInstruction = instruction;
    }

    /**
     * Returns true if a given string is a valid instruction.
     */
    public static boolean isValidInstruction(String test) {
        return test.matches(INSTRUCTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullInstruction;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && fullInstruction.equals(((Instruction) other).fullInstruction)); // state check
    }

    @Override
    public int hashCode() {
        return fullInstruction.hashCode();
    }

}
