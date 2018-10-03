package seedu.address.model.classroom;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
/**
 This classroom manager stores classroom for Trajectory.
 */
public class ClassroomManager {
    @XmlElementWrapper(name = "classrooms")
    @XmlElement(name = "class")
    private ArrayList<Classroom> classroomList = new ArrayList<Classroom>();
    public ArrayList<Classroom> getList() {
        return classroomList;
    }
    public void setCourseList(ArrayList<Classroom> classroomList) {
        this.classroomList = classroomList;
    }
}
