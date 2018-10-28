package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class DrinkCommandResult {

    public final String feedbackToUser;

    public DrinkCommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
    }

}
