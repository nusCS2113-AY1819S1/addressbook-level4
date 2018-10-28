package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "LoanList")
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoanList {
    @XmlElement(name = "Loaner")
    private List<XmlAdaptedLoanerDescription> xmlAdaptedLoanList = null;

    public XmlAdaptedLoanList() {
        xmlAdaptedLoanList = new ArrayList<>();
    }
    public List<XmlAdaptedLoanerDescription> getLoanList() {
        return xmlAdaptedLoanList;
    }
    public void setLoanList(List<XmlAdaptedLoanerDescription> xmlAdaptedLoanList) {
        this.xmlAdaptedLoanList = xmlAdaptedLoanList;
    }
    public void addLoaner(XmlAdaptedLoanerDescription toAdd) {
        xmlAdaptedLoanList.add(toAdd);
    }


}
