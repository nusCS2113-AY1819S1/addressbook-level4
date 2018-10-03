package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.model.item.Item;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Item.
 */
public class ItemUtil {

    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddCommand(Item item) {
        return AddCommand.COMMAND_WORD + " " + getItemDetails(item);
    }

    /**
     * Returns the part of command string for the given {@code item}'s details.
     */
    public static String getItemDetails(Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + item.getName().fullName + " ");
        sb.append(PREFIX_QUANTITY + String.valueOf(item.getQuantity()) + " ");
        sb.append(PREFIX_MIN_QUANTITY + String.valueOf(item.getMinQuantity()) + " ");
        sb.append(PREFIX_STATUS + String.valueOf(item.getStatus()) + " ");
        item.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditItemDescriptorDetails(EditItemDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getQuantity().ifPresent(quantity -> sb.append(PREFIX_QUANTITY).append(quantity).append(" "));
        descriptor.getMinQuantity().ifPresent(minQuantity -> sb.append(PREFIX_MIN_QUANTITY).append(minQuantity).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status).append(" "));
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
