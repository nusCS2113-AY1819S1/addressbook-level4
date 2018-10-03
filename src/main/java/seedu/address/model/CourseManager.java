package seedu.address.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
This course manager stores courses for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
public class CourseManager {

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    private ArrayList<Course> courseList = new ArrayList<Course>();

    public ArrayList<Course> getList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }


}



