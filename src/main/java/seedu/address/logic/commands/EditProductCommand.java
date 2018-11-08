package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.RemainingItems;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing product in the productInfo book.
 */

public class EditProductCommand extends Command {

    public static final String COMMAND_WORD = "editproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the product identified "
            + "by the index number used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SERIAL_NR + "Serial Number] "
            + "[" + PREFIX_DISTRIBUTOR + "Distributor] "
            + "[" + PREFIX_PRODUCT_INFO + "PRODUCT INFO] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SERIAL_NR + "91234567 "
            + PREFIX_DISTRIBUTOR + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Product: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "A product with the same serialnumber already exists.";

    private final Index index;
    private final EditProductDescriptor editProductDescriptor;

    /**
     * @param index of the product in the filtered product list to edit
     * @param editProductDescriptor details to edit the product with
     */

    public EditProductCommand(Index index, EditProductDescriptor editProductDescriptor) {
        requireNonNull(index);
        requireNonNull(editProductDescriptor);

        this.index = index;
        this.editProductDescriptor = new EditProductDescriptor(editProductDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToEdit = lastShownList.get(index.getZeroBased());
        Product editedProduct = createEditedProdcut(productToEdit, editProductDescriptor);

        if (!productToEdit.isSameProduct(editedProduct) && model.hasProduct(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.updateProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct));
    }

    /**
     * Creates and returns a {@code Product} with the details of {@code productToEdit}
     * edited with {@code editProductDescriptor}.
     */
    private static Product createEditedProdcut(Product productToEdit, EditProductDescriptor editProductDescriptor) {
        assert productToEdit != null;


        Name updatedName = editProductDescriptor.getName().orElse(productToEdit.getName());
        SerialNumber updatedSerialNumber =
                editProductDescriptor.getSerialNumber().orElse(productToEdit.getSerialNumber());
        DistributorName updatedDistName = editProductDescriptor.getDistributor().orElse(productToEdit.getDistributor());
        ProductInfo updatedProductInfo = editProductDescriptor.getProductInfo().orElse(productToEdit.getProductInfo());
        RemainingItems updatedRemainingItems =
                editProductDescriptor.getRemainingItems().orElse(productToEdit.getRemainingItems());
        Set<Tag> updatedTags = editProductDescriptor.getTags().orElse(productToEdit.getTags());
        return new Product(updatedName, updatedSerialNumber,
                updatedDistName, updatedProductInfo, updatedRemainingItems, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProductCommand)) {
            return false;
        }

        // state check
        EditProductCommand e = (EditProductCommand) other;
        return index.equals(e.index)
                && editProductDescriptor.equals(e.editProductDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */

    public static class EditProductDescriptor {
        private Name name;
        private SerialNumber serialNumber;
        private DistributorName distname;
        private ProductInfo productInfo;
        private RemainingItems remainingItems;
        private Set<Tag> tags;

        public EditProductDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
        */
        public EditProductDescriptor(EditProductDescriptor toCopy) {
            setName(toCopy.name);
            setSerialNumber(toCopy.serialNumber);
            setEmail(toCopy.distname);
            setProductInfo(toCopy.productInfo);
            setRemainingItems(toCopy.remainingItems);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
        */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, serialNumber, distname, productInfo, remainingItems, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSerialNumber(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Optional<SerialNumber> getSerialNumber() {
            return Optional.ofNullable(serialNumber);
        }

        public void setEmail(DistributorName distname) {
            this.distname = distname;
        }

        public Optional<DistributorName> getDistributor() {
            return Optional.ofNullable(distname);
        }

        public void setProductInfo(ProductInfo productInfo) {
            this.productInfo = productInfo;
        }

        public Optional<ProductInfo> getProductInfo() {
            return Optional.ofNullable(productInfo);
        }

        public void setRemainingItems(RemainingItems remainingItems) {
            this.remainingItems = remainingItems;
        }

        public Optional<RemainingItems> getRemainingItems() {
            return Optional.ofNullable(remainingItems);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
        */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
          */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditProductDescriptor)) {
                return false;
            }

            // state check
            EditProductDescriptor e = (EditProductDescriptor) other;

            return getName().equals(e.getName())
                    && getSerialNumber().equals(e.getSerialNumber())
                    && getDistributor().equals(e.getDistributor())
                    && getProductInfo().equals(e.getProductInfo())
                    && getRemainingItems().equals(e.getRemainingItems())
                    && getTags().equals(e.getTags());
        }
    }
}
