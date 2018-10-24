package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.commons.core.Messages;

import java.util.List;

/**
 * Lists all persons in the address book to the user.
 */
public class CheckExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET_check";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_INVALID_FORMAT = "Invalid command format!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Check expenditures in a specific period\n"
            + "Parameters: Date1 ( must be a positive number)"
            + " Date2 (must larger than previous number)\n"
            + "Examples: " + COMMAND_WORD
            + PREFIX_DATE + "27-09-2018"
            + PREFIX_DATE + "09-10-2018";

    public static final String MESSAGE_SUCCESS = "Total money in this period %f";

    private final Date date1;
    private final Date date2;

    public CheckExpenditureCommand(Date date1, Date date2) {
        requireNonNull(date1);
        requireNonNull(date2);
        this.date1 = date1;
        this.date2 = date2;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Expenditure> lastShownList = model.getFilteredExpenditureList();
        Expenditure editedExpenditure = lastShownList.get(0);



        int index = 0;
        float total = 0;
        while (index <= lastShownList.size()) {


            editedExpenditure = lastShownList.get(index);

            int year1 = Integer.valueOf(date1.toString().substring(6));
            int month1 = Integer.valueOf(date1.toString().substring(3, 5));
            int day1 = Integer.valueOf(date1.toString().substring(0, 2));

            int year2 = Integer.parseInt(date2.toString().substring(6));
            int month2 = Integer.parseInt(date2.toString().substring(3, 5));
            int day2 = Integer.parseInt(date2.toString().substring(0, 2));


            int year = Integer.parseInt(editedExpenditure.getDate().toString().substring(6));
            int month = Integer.parseInt(editedExpenditure.getDate().toString().substring(3, 5));
            int day = Integer.parseInt(editedExpenditure.getDate().toString().substring(0, 2));

            if ((year1 > year) || (year2 < year)){return new CommandResult(String.format(MESSAGE_INVALID_FORMAT));}
            else if ((month1 > month) || (month2 < month)){return new CommandResult(String.format(MESSAGE_INVALID_FORMAT));}
            else if ((day1 > day) || (day2 < day)) {return new CommandResult(String.format(MESSAGE_INVALID_FORMAT));}
            else {
                total = total + Integer.parseInt(editedExpenditure.getMoney().toString());
            }
            index++;


        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, total));
    }
}
