package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;

/**
 * Provides a handle to a book card in the book list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String QUANTITY_FIELD_ID = "#quantity";
    private static final String ISBN_FIELD_ID = "#isbn";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label quantityLabel;
    private final Label isbnLabel;
    private final Label priceLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
        isbnLabel = getChildNode(ISBN_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    public String getPhone() {
        return isbnLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code book}.
     */
    public boolean equals(Book book) {
        return getName().equals(book.getName().fullName)
                && getQuantity().equals(book.getQuantity().getValue())
                && getPhone().equals(book.getIsbn().value)
                && getPrice().equals(book.getPrice().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(book.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
