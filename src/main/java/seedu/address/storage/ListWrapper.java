package seedu.address.storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper {

    @XmlElementWrapper
    private ArrayList<XmlAdaptedTimeSlots> list;

    public void setList(ArrayList<XmlAdaptedTimeSlots> list) {
        this.list = list;
    }

    public ArrayList<XmlAdaptedTimeSlots> getList(){
        return list;
    }
}