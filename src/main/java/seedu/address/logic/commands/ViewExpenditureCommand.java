package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Lists particular expenditures in the Expenditure Tracker to the user.
 */
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

        Predicate<Expenditure> predicateShowExpendituresOnDate = model.getPredicateShowExpendituresOnDate(filter);
        Predicate<Expenditure> predicateShowExpendituresOfCategory = model
                .getPredicateShowExpendituresOfCategory(filter);

        if (filter.matches(DATE_VALIDATION_REGEX)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOnDate);
            return new CommandResult(String.format(MESSAGE_SUCCESS_DATE, filter));
        } else if (filter.equals("Food")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Drink")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Clothing")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Electronics")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("DailyNecessities")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Sports")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Communications")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Travels")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Study")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Office")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Pets")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Gifts")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Entertainment")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Traffic")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Shopping")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Beauty")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if (filter.equals("Furniture")) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }
}
