//@@author XiaoYunhan
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.task.Task;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node>{
    
    private static final String ID_FIELD_ID = "#id";
    private static final String TASK_NAME_FIELD_ID = "#taskname";
    private static final String TASK_MODULE_FIELD_ID = "#taskmodule";
    private static final String TASK_DATE_FIELD_ID = "#taskdate";
    private static final String TASK_PRIORITY_FIELD_ID = "#taskpriority";
    private static final String TASK_STATE_FIELD_ID = "#taskstate";

    private final Label idLabel;
    private final Label taskNameLabel;
    private final Label taskModuleLabel;
    private final Label taskDateLabel;
    private final Label taskPriorityLabel;
    private final Label taskStateLabel;

    public TaskCardHandle(Node cardNode) {

        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        taskNameLabel = getChildNode(TASK_NAME_FIELD_ID);
        taskModuleLabel = getChildNode(TASK_MODULE_FIELD_ID);
        taskDateLabel = getChildNode(TASK_DATE_FIELD_ID);
        taskPriorityLabel = getChildNode(TASK_PRIORITY_FIELD_ID);
        taskStateLabel = getChildNode(TASK_STATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTaskName() {
        return taskNameLabel.getText();
    }

    public String getTaskModule() {
        return taskModuleLabel.getText();
    }

    public String getTaskDate() {
        return taskDateLabel.getText();
    }

    public String getTaskPriority() {
        return taskPriorityLabel.getText();
    }

    public String getTaskState() {
        return taskStateLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code task}
     */
    public boolean equals(Task task) {
        return getTaskName().equals(task.getName().toString())
                && getTaskDate().equals(task.getDate().toString())
                && getTaskModule().equals(task.getModule().toString())
                && getTaskPriority().equals(task.getPriority().toString())
                && getTaskState().equals(task.getComplete() ? "Status: Completed" : "Status: Uncompleted");
    }

}
