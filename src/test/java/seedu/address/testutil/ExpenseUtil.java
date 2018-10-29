package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.expense.Expense;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Expense.
 */
public class ExpenseUtil {

    /**
     * Returns an add expense command string for adding the {@code expense}.
     */
    public static String getAddExpenseCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EXPENSE_CATEGORY + expense.getExpenseCategory().expenseCategory + " ");
        sb.append(PREFIX_EXPENSE_VALUE + expense.getExpenseValue().expenseValue + " ");
        sb.append(PREFIX_EXPENSE_DATE + expense.getExpenseDate().expenseDate + " ");
        expense.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getExpenseCategory().ifPresent(expenseCategory ->
                sb.append(PREFIX_EXPENSE_CATEGORY).append(expenseCategory.expenseCategory).append(" "));
        descriptor.getExpenseDate().ifPresent(expenseDate ->
                sb.append(PREFIX_EXPENSE_DATE).append(expenseDate.expenseDate).append(" "));
        descriptor.getExpenseValue().ifPresent(expenseValue ->
                sb.append(PREFIX_EXPENSE_VALUE).append(expenseValue.expenseValue).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
