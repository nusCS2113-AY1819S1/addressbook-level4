package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks the expenditures and the tasks on a particular day.  "
            + "Parameters: "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "25-12-2017 ";

    public static final String MESSAGE_SUCCESS = "Expenditures and tasks on %s listed. \n%s";

    private String information;
    private String theDate;


    /**
     * Creates an AddExpenditureCommand to add the specified {@code Expenditure}
     */
    public CheckCommand(String date){
        requireNonNull(date);
        theDate = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        information = model.checkRecords(theDate);
        model.commitAddressBook();
        PopUpString pps = new PopUpString(information);
        pps.popup();
        return new CommandResult(String.format(MESSAGE_SUCCESS, theDate, information));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
    */
}
