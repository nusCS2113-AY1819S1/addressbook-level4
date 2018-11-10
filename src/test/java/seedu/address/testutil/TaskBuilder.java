package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DEADLINE = "1/1/2018";
    public static final String DEFAULT_MODULECODE = "CS2113";
    public static final String DEFAULT_TITLE = "Complete code refactoring";
    public static final String DEFAULT_DESCRIPTION = "refer to notes";
    public static final String DEFAULT_PRIORITY = "high";
    public static final boolean DEFAULT_COMPLETE = false;
    public static final String DEFAULT_EXPECTED_NUM_OF_HOURS = "1";
    public static final String DEFAULT_COMPLETED_NUM_OF_HOURS = "-1";
    public static final List<Milestone> DEFAULT_MILESTONES = new ArrayList<>();
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private Deadline deadline;
    private ModuleCode moduleCode;
    private String title;
    private String description;
    private PriorityLevel priority;
    private boolean isCompleted;
    private int expectedNumOfHours;
    private int completedNumOfHours;
    private List<Milestone> milestones;
    private Set<Tag> tags;

    public TaskBuilder() {
        this.deadline = new Deadline(DEFAULT_DEADLINE);
        this.moduleCode = null;
        this.title = DEFAULT_TITLE;
        this.description = DEFAULT_DESCRIPTION;
        this.priority = new PriorityLevel(DEFAULT_PRIORITY);
        this.isCompleted = DEFAULT_COMPLETE;
        this.completedNumOfHours = Integer.parseInt(DEFAULT_COMPLETED_NUM_OF_HOURS);
        this.expectedNumOfHours = Integer.parseInt(DEFAULT_EXPECTED_NUM_OF_HOURS);
        this.milestones = DEFAULT_MILESTONES;
        this.tags = DEFAULT_TAGS;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        this.deadline = taskToCopy.getDeadline();
        this.moduleCode = taskToCopy.getModuleCode();
        this.title = taskToCopy.getTitle();
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriorityLevel();
        this.isCompleted = taskToCopy.isCompleted();
        this.expectedNumOfHours = taskToCopy.getExpectedNumOfHours();
        this.completedNumOfHours = taskToCopy.getCompletedNumOfHours();
        this.milestones = taskToCopy.getMilestoneList();
        this.tags = new HashSet<>(taskToCopy.getTags());
    }
    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the ModuleCode of the {@code Task} that we are building.
     */
    public TaskBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the Title of the {@code Task} that we are building.
     */
    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new PriorityLevel(priority);
        return this;
    }

    /**
     * Sets the completed status of the {@code Task} that we are building.
     */
    public TaskBuilder withCompletedNumOfHours(int hours) {
        this.isCompleted = true;
        this.completedNumOfHours = hours;
        return this;
    }

    /**
     * Sets the expected number of hours of the {@code Task} that we are building.
     */
    public TaskBuilder withExpectedNumOfHours(int expectedNumOfHours) {
        this.expectedNumOfHours = expectedNumOfHours;
        return this;
    }

    /**
     * Sets the milestones of the {@code Task} that we are building.
     */
    public TaskBuilder withMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Build the task with the parameters set
     * @return Task
     */
    public Task build() {
        return new Task(deadline, moduleCode, title, description, priority,
                expectedNumOfHours, completedNumOfHours, isCompleted, milestones, tags);
    }
}
