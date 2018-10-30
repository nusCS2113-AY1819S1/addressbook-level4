package seedu.address.storage;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB-friendly loan list.
 */

@XmlRootElement(name = "LoanList")
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoanList {
    @XmlElement(name = "Loaner")
    private ArrayList<XmlAdaptedLoanerDescription> xmlAdaptedLoanList = null;

    public XmlAdaptedLoanList() {
        xmlAdaptedLoanList = new ArrayList<>();
    }
    public XmlAdaptedLoanList(ArrayList<XmlAdaptedLoanerDescription> xmlAdaptedLoanList) {
        this.xmlAdaptedLoanList = xmlAdaptedLoanList;
    }
    public ArrayList<XmlAdaptedLoanerDescription> getLoanList() {
        return xmlAdaptedLoanList;
    }

    public void addLoaner(XmlAdaptedLoanerDescription toAdd) {
        xmlAdaptedLoanList.add(toAdd);
    }

}
