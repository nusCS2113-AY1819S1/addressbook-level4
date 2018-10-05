package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.item.Item;

/**
 * Provides a handle to a item card in the item list panel.
 */
public class ItemCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String QUANTITY_FIELD_ID = "#quantity";
    private static final String MIN_QUANTITY_FIELD_ID = "#min_quantity";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label quantityLabel;
    private final Label minQuantityLabel;
    private final Label statusLabel;
    private final List<Label> tagLabels;

    public ItemCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
        minQuantityLabel = getChildNode(MIN_QUANTITY_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);

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

    public String getMinQuantity() {
        return minQuantityLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(Item item) {
        return getName().equals(item.getName().fullName)
                && getQuantity().equals(item.getQuantity().toString())
                && getMinQuantity().equals(item.getMinQuantity().toString())
                && getStatus().equals(item.getStatus().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(item.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
