package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Record.
 */
public class RecordUtil {

    /**
     * Returns an add command string for adding the {@code record}.
     */
    public static String getAddCommand(Record record) {
        return AddCommand.COMMAND_WORD + " " + getRecordDetails(record);
    }

    /**
     * Returns the part of command string for the given {@code record}'s details.
     */
    public static String getRecordDetails(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + record.getName().fullName + " ");
        sb.append(PREFIX_DATE + record.getDate().value + " ");
        sb.append(PREFIX_INCOME + record.getIncome().value + " ");
        sb.append(PREFIX_EXPENSE + record.getExpense().value + " ");
        record.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecordDescriptor}'s details.
     */
    public static String getEditRecordDescriptorDetails(EditRecordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        descriptor.getIncome().ifPresent(income -> sb.append(PREFIX_INCOME).append(income.value).append(" "));
        descriptor.getExpense().ifPresent(expense -> sb.append(PREFIX_EXPENSE).append(expense.value).append(" "));
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
