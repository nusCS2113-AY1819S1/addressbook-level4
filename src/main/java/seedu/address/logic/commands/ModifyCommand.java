package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Modify the user profile in the Profile window.
 */
public class ModifyCommand extends Command {

    public static final String COMMAND_WORD = "modify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modify the user profile in the Profile window \n"
            + "Parameters: "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_DURATION + "DURATION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GENDER + "female "
            + PREFIX_USERNAME + "sarah "
            + PREFIX_HEIGHT + "1.69 "
            + PREFIX_WEIGHT + "87.2 "
            + PREFIX_CALORIES + "150 "
            + PREFIX_DIFFICULTY + "beginner "
            + PREFIX_DURATION + "15m";

    public static final String MESSAGE_SUCCESS = "User profile has been modified!";

    private final String gender;
    private final String username;
    private final String height;
    private final String weight;
    private final String difficulty;
    private final String duration;
    private final String calories;
    public ModifyCommand(String gender, String username, String height, String weight, String calories,
                         String difficulty, String duration) {

        this.gender = gender;
        this.username = username;
        this.height = height;
        this.weight = weight;
        this.difficulty = difficulty;
        this.duration = duration;
        this.calories = calories;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ModifyCommand)) {
            return false;
        }
        // state check
        ModifyCommand e = (ModifyCommand) other;
        return gender.equals(e.gender)
                && username.equals(e.username)
                && height.equals(e.height)
                && weight.equals(e.weight)
                && difficulty.equals(e.difficulty)
                && duration.equals(e.duration)
                && calories.equals(e.calories);
    }
}
