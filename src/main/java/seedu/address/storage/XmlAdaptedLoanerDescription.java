package seedu.address.storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.item.LoanerDescription;

/**
 * JAXB-friendly version of the LoanerDescription.
 */
@XmlRootElement(name = "Loaner")
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoanerDescription {
    private String itemName;
    private String loanerName;
    private Integer quantity;

    public XmlAdaptedLoanerDescription() {

    }
    public XmlAdaptedLoanerDescription(String itemName, String loanerName, Integer quantity) {
        this.itemName = itemName;
        this.loanerName = loanerName;
        this.quantity = quantity;
    }
    public XmlAdaptedLoanerDescription(LoanerDescription loaner) {
        itemName = loaner.getItemName().toString();
        loanerName = loaner.getLoanerName().toString();
        quantity = loaner.getQuantity().toInteger();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
