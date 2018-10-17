package seedu.address.storage.serializable;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.course.Course;
import seedu.address.storage.adapter.XmlAdaptedCourse;




/**
This course manager stores courses for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
public class XmlSerializableCourseList {

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    private ArrayList<XmlAdaptedCourse> courseList = new ArrayList<XmlAdaptedCourse>();

    public XmlSerializableCourseList() {
        courseList = new ArrayList<>();
    }

    public XmlSerializableCourseList(ArrayList<Course> src) {
        courseList.addAll(src.stream().map(XmlAdaptedCourse::new).collect(Collectors.toList()));
    }

    public ArrayList<XmlAdaptedCourse> getList() {
        return courseList;
    }

    public void setCourseList(ArrayList<XmlAdaptedCourse> courseList) {
        this.courseList = courseList;
    }




}



