package seedu.address.ui;

//import guitests.guihandles.TaskListPanelHandle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.address.commons.events.ui.JumpToListRequestEvent;
//import seedu.address.commons.util.FileUtil;
//import seedu.address.model.task.Task;

//import java.nio.file.Path;
//import java.nio.file.Paths;

//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
//import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

public class TaskListPanelTest {
    /*
    private static final ObservableList<Task> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TASK);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TaskListPanelHandle taskListPanelHandle;

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    /*
    private Path createXmlFileWithPersons(int taskCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < taskCount; i++) {
            builder.append("<tasks>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</tasks>\n");
        }
        builder.append("</addressbook>\n");

        Path manyTasksFile = Paths.get(TEST_DATA_FOLDER + "manyTasks.xml");
        FileUtil.createFile(manyTasksFile);
        FileUtil.writeToFile(manyTasksFile, builder.toString());
        manyTasksFile.toFile().deleteOnExit();
        return manyTasksFile;
    }

    /**
     * Initializes {@code taskListPanelHandle} with a {@code TaskListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code TaskListPanel}.
     */
    /*
    private void initUi(ObservableList<Task> backingList) {
        TaskListPanel taskListPanel = new TaskListPanel(backingList);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }
    */

}
