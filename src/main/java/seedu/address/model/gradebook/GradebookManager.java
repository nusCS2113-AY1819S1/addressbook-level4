package seedu.address.model.gradebook;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 This gradebook manager stores gradebook information for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
public class GradebookManager {
    @XmlElementWrapper(name = "gradebook")
    @XmlElement(name = "gradeItem")
    private ArrayList<GradebookComponent> gradebookComponentsList = new ArrayList<GradebookComponent>();

    public ArrayList<GradebookComponent> getList() {
        return gradebookComponentsList;
    }
    public void setGradebookComponentList(ArrayList<GradebookComponent> gradebookComponentsList) {
        this.gradebookComponentsList = gradebookComponentsList;
    }
}


