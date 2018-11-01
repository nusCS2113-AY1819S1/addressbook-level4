//@@author SHININGGGG
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
            + "Parameters: DATE, CATEGORY or all\n"
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
        } else if ("Food".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Drink".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Clothing".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Electronics".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("DailyNecessities".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Sports".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Communications".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Travels".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Study".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Office".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Pets".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Gifts".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Entertainment".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Traffic".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Shopping".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Beauty".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else if ("Furniture".equals(filter)) {
            model.updateFilteredExpenditureList(predicateShowExpendituresOfCategory);
            return new CommandResult(String.format(MESSAGE_SUCCESS_CATEGORY, filter));
        } else {
            model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }
}
