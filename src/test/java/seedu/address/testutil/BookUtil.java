package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Book.
 */
public class BookUtil {

    /**
     * Returns an add command string for adding the {@code book}.
     */
    public static String getAddCommand(Book book) {
        return AddCommand.COMMAND_WORD + " " + getBookDetails(book);
    }

    /**
     * Returns the part of command string for the given {@code book}'s details.
     */
    public static String getBookDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + book.getName().fullName + " ");
        sb.append(PREFIX_ISBN + book.getIsbn().value + " ");
        sb.append(PREFIX_PRICE + book.getPrice().value + " ");
        sb.append(PREFIX_QUANTITY + book.getQuantity().getValue() + " ");
        book.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBookDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditBookDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getIsbn().ifPresent(phone -> sb.append(PREFIX_ISBN).append(phone.value).append(" "));
        descriptor.getPrice().ifPresent(email -> sb.append(PREFIX_PRICE).append(email.value).append(" "));
        descriptor.getQuantity().ifPresent(address -> sb.append(PREFIX_QUANTITY).append(address.getValue()).append(" "));
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
