package seedu.address.model.classroom;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
/**
 This classroom manager stores classroom for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassroomManager {
    @XmlElementWrapper(name = "classrooms")
    @XmlElement(name = "class")
    private ArrayList<Classroom> classroomList = new ArrayList<Classroom>();
    public ArrayList<Classroom> getList() {
        return classroomList;
    }
    public void setClassroomList(ArrayList<Classroom> classroomList) {
        this.classroomList = classroomList;
    }
}
