package seedu.address.storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.item.Loaner;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoaner {
    @XmlElement(required = true)
    private Name itemName;
    @XmlElement(required = true)
    private Name loanerName;
    @XmlElement(required = true)
    private Quantity quantity;

    public XmlAdaptedLoaner() {}

    public XmlAdaptedLoaner(Name itemName, Name loanerName, Quantity quantity) {
        this.itemName = itemName;
        this.loanerName = loanerName;
        this.quantity = quantity;
    }
    public XmlAdaptedLoaner(Loaner loaner) {
        itemName = loaner.getItemName();
        loanerName = loaner.getLoanerName();
        quantity = loaner.getQuantity();
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(loanerName)
                .append(" loanerName: ")
                .append(loanerName)
                .append(" itemName: ")
                .append(itemName)
                .append(" Quantity: ")
                .append(quantity);
        return builder.toString();
    }
}
