package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Item.
 */
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String quantity;
    @XmlElement(required = true)
    private String minQuantity;
    @XmlElement(required = true)
    private List<Integer> status;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String name, String quantity, String minQuantity, List<Integer> status,
                          List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.status = new ArrayList<>(status);
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedItem
     */
    public XmlAdaptedItem(Item source) {
        name = source.getName().fullName;
        quantity = source.getQuantity().toString();
        minQuantity = source.getMinQuantity().toString();
        status = source.getStatus();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Quantity"));
        }
        if(Integer.parseInt(quantity) <= 0) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity.toString());

        if (minQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Minimum Quantity"));
        }
        if(Integer.parseInt(minQuantity) <= 0) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelMinQuantity = new Quantity(minQuantity.toString());

        final Set<Tag> modelTags = new HashSet<>(itemTags);
        return new Item(modelName, modelQuantity, modelMinQuantity, status, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherItem = (XmlAdaptedItem) other;
        return Objects.equals(name, otherItem.name)
                && Objects.equals(quantity, otherItem.quantity)
                && Objects.equals(minQuantity, otherItem.minQuantity)
                && Objects.equals(status, otherItem.status)
                && tagged.equals(otherItem.tagged);
    }
}
