package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.model.expenditureinfo.Expenditure;
import java.util.function.Predicate;
import seedu.address.model.Model;
import seedu.address.logic.CommandHistory;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

public class ViewExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET_view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of expenditures made on a particular day or of a specific category "
            + "in the displayed Expenditure Tracker.\n"
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 01-01-2018";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all expenditures";
    public static final String MESSAGE_SUCCESS_DATE = "Listed expenditures on %s";
    public static final String MESSAGE_SUCCESS_CATEGORY = "Listed expenditures of %s category";

    public static final String DATE_VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}" + "-" + "[\\d]{4}";

    private final String filter;

    /**
     * @param filter to determine the predicate used for updating filtered task list
     */
    public ViewExpenditureCommand(String filter) {
        requireNonNull(filter);

        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Predicate<Expenditure> PREDICATE_SHOW_EXPENDITURES_ON_DATE = model.getPredicateShowExpendituresOnDate(filter);
        Predicate<Expenditure> PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY = model.getPredicateShowExpendituresOfCategory(filter);

        if (filter.matches(DATE_VALIDATION_REGEX)) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_ON_DATE);
            return new CommandResult(String.format(MESSAGE_SUCCESS_DATE, filter));
        } else if (filter.equals("Food")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Drink")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Clothing")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Electronics")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("DailyNecessities")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Sports")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Communications")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Travels")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Study")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Office")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Pets")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Gifts")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Entertainment")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Traffic")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Shopping")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Beauty")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Furniture")) {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_EXPENDITURES_OF_CATEGORY);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }
}
