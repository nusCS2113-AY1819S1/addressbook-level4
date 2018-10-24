package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Tracks the productivity of tasks
 * for the previous week based on hours
 */
public class TrackProductivityCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "track";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tracks your productivity.";

    public static final String MESSAGE_SUCCESS = "Recent productvity: %1$s";

    private static final Logger logger = LogsCenter.getLogger(TrackProductivityCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // filter out Completed tasks
        model.trackProductivity();
        ObservableList<Task> tasks = model.getAddressBook().getTaskList();
        logger.info("No. of completed tasks: " + tasks.size());
        double productivity = calculateProductivity(tasks);
        String result = Integer.toString((int) (productivity * 100)) + " %";
        return new CommandResult(String.format(MESSAGE_SUCCESS, result));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new TrackProductivityCommand();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Calculates the overall productivity for completed
     * @param tasks
     * @return the average productivity
     */
    public double calculateProductivity(ObservableList<Task> tasks) {
        double averageProductivity;
        if (tasks.size() == 0) {
            averageProductivity = 1;
        } else {
            double totalProductivity = 0;
            for (Task task: tasks) {
                double taskProductivity = (double) task.getExpectedNumOfHours() / task.getCompletedNumOfHours();
                totalProductivity += taskProductivity;
            }
            averageProductivity = totalProductivity / tasks.size();
        }
        return averageProductivity;
    }
}
