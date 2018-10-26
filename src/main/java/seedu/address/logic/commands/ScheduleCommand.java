package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Schedule;

//@@Author: driedmelon
/**
 * Adds the schedule of a person to the address book
 */

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_ALIAS = "sc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE(DDMMYYYY) "
            + PREFIX_START_TIME + "TIME(24HRS) "
            + PREFIX_END_TIME + "TIME(24HRS) "
            + PREFIX_EVENT_NAME + "EVENT NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Sylvester Chun "
            + PREFIX_DATE + "08112018 "
            + PREFIX_START_TIME + "1000 "
            + PREFIX_END_TIME + "1200 "
            + PREFIX_EVENT_NAME + "GER1000 Class ";

    public static final String MESSAGE_SUCCESS = "Schedule Added!";

    private Schedule toSchedule;

    public ScheduleCommand(Schedule schedule) {
        requireNonNull(schedule);
        toSchedule = schedule;
    }

    public final String MESSAGE_FAILURE = "THIS IS A NEW TEST MESSAGE";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

//        model.addSchedule(toSchedule);

//        throw new CommandException(MESSAGE_FAILURE);

        return new CommandResult(MESSAGE_SUCCESS);

    }
}
