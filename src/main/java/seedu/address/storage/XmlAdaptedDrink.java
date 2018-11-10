package seedu.address.storage;

import java.util.*;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;


/**
 * JAXB-friendly version of the Drink.
 */
public class XmlAdaptedDrink {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Drink's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String sellingPrice;
    @XmlElement(required = true)
    private String costPrice;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedDrink.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDrink() {}

    /**
     * Constructs an {@code XmlAdaptedDrink} with the given drink details.
     */
    public XmlAdaptedDrink(String name, String sellingPrice, String costPrice, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.costPrice = costPrice;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Drink into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedDrink(Drink source) {
        name = source.getName().toString();
        sellingPrice = source.getRetailPrice().toString();
        costPrice = source.getCostPrice().toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Drink toModelType() throws IllegalValueException {
        final List<Tag> drinkTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            drinkTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (sellingPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(sellingPrice)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelSellingPrice = new Price(sellingPrice);

        if (costPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(costPrice)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelCostPrice = new Price(costPrice);

        final Set<Tag> modelTags = new HashSet<>(drinkTags);
        return new Drink(modelName, modelSellingPrice, modelCostPrice, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDrink)) {
            return false;
        }

        XmlAdaptedDrink otherDrink = (XmlAdaptedDrink) other;
        return Objects.equals(name, otherDrink.name)
                && Objects.equals(sellingPrice, otherDrink.sellingPrice)
                && Objects.equals(costPrice, otherDrink.costPrice)
                && tagged.equals(otherDrink.tagged);
    }
}
