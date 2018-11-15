package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMAINING_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditProductCommand.EditProductDescriptor;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;


//@@garagaristahir
/**
 * A utility class for Product.
 */
public class ProductUtil {

    /**
     * Returns an add command string for adding the {@code product}.
     */
    public static String getAddCommand(Product product) {
        return AddCommand.COMMAND_WORD + " " + getProductDetails(product);
    }

    /**
     * Returns the part of command string for the given {@code product}'s details.
     */
    public static String getProductDetails(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + product.getName().fullName + " ");
        sb.append(PREFIX_SERIAL_NR + product.getSerialNumber().value + " ");
        sb.append(PREFIX_DISTRIBUTOR + product.getDistributor().fullDistName + " ");
        sb.append(PREFIX_PRODUCT_INFO + product.getProductInfo().value + " ");
        sb.append(PREFIX_REMAINING_ITEMS + product.getRemainingItems().value + " ");
        product.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditProductDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditProductDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getSerialNumber().ifPresent(phone -> sb.append(PREFIX_SERIAL_NR).append(phone.value).append(" "));
        descriptor.getDistributor().ifPresent(email ->
                sb.append(PREFIX_DISTRIBUTOR).append(email.fullDistName).append(" "));
        descriptor.getProductInfo().ifPresent(address -> sb.append(PREFIX_PRODUCT_INFO).append(address.value)
                .append(" "));
        descriptor.getRemainingItems().ifPresent(remainingItems ->
                sb.append(PREFIX_REMAINING_ITEMS).append(remainingItems.value).append(" "));
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
