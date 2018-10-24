package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.storage.StorageManager;

import java.util.List;

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
            + "Examples:" + COMMAND_WORD + "27-09-2018"
            + "09-10-2018";

    public static final String MESSAGE_SUCCESS = "Show total expenditures in this period %f";

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

            int year1 = Integer.valueOf(date1.toString().substring(6, 10));
            int month1 = Integer.valueOf(date1.toString().substring(3, 5));
            int day1 = Integer.valueOf(date1.toString().substring(0, 2));

            int year2 = Integer.valueOf(date2.toString().substring(6, 10));
            int month2 = Integer.valueOf(date2.toString().substring(3, 5));
            int day2 = Integer.valueOf(date2.toString().substring(0, 2));


            int year = Integer.valueOf(editedExpenditure.getDate().toString().substring(6, 10));
            int month = Integer.valueOf(editedExpenditure.getDate().toString().substring(3, 5));
            int day = Integer.valueOf(editedExpenditure.getDate().toString().substring(0, 2));
            if ((year1 >= year) || (year2 <= year));
            else if ((month1 >= month) || (month2 <= month)) ;
            else if ((day1 >= day) || (day2 <= day)) ;
            else {
                total = total + Integer.valueOf(editedExpenditure.getMoney().toString());
            }
            index++;


        }
        model.updateExpenditure(editedExpenditure, editedExpenditure);
        model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, total));
    }
}
