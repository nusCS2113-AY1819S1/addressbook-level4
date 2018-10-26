package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;




/**
 * Lists all persons in the address book to the user.
 */
public class CheckExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET_check";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Check expenditures in a specific period\n"
            + "Parameters: Date1 ( must be a positive number)"
            + " Date2 (must larger than previous number)\n"
            + "Examples: " + COMMAND_WORD + " "
            + PREFIX_START + "27-09-2018 "
            + PREFIX_END + "09-10-2018 ";

    public static final String MESSAGE_SUCCESS = "Total money in this period %f";

    private final String date1;
    private final String date2;


    public CheckExpenditureCommand(String d1, String d2) {
        requireNonNull(d1);
        requireNonNull(d2);
        date1 = d1;
        date2 = d2;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        List<Expenditure> lastShownList = model.getFilteredExpenditureList();
        Expenditure editedExpenditure;


        int index = 0;
        float total = 0;
        while (index < lastShownList.size()) {
            editedExpenditure = lastShownList.get(index);

            int year1 = Integer.parseInt(date1.substring(6));
            int month1 = Integer.parseInt(date1.substring(3, 5));
            int day1 = Integer.parseInt(date1.substring(0, 2));

            int year2 = Integer.parseInt(date2.substring(6));
            int month2 = Integer.parseInt(date2.substring(3, 5));
            int day2 = Integer.parseInt(date2.substring(0, 2));


            int year = Integer.parseInt(editedExpenditure.getDate().toString().substring(6));
            int month = Integer.parseInt(editedExpenditure.getDate().toString().substring(3, 5));
            int day = Integer.parseInt(editedExpenditure.getDate().toString().substring(0, 2));

            if ((year1 < year) && (year2 > year)) {
                total = total + Integer.parseInt(editedExpenditure.getMoney().toString());
            }
            else if (((year1 == year) && (year2 == year)) || ((year1 == year) && (year2 > year))
                        || ((year1 < year) && (year2 == year))) {
                if ((month1 < month) && (month2 > month)) {
                    total = total + Integer.parseInt(editedExpenditure.getMoney().toString());
                }
                else if (((month1 == month) || (month2 == month)) && ((day1 <= day) && (day2 >= day))) {
                    total = total + Integer.parseInt(editedExpenditure.getMoney().toString());
                }
            }
            index++;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, total));
    }
}
