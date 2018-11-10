package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getTaskName().fullName + " ");
        sb.append(PREFIX_BODY + task.getBody().bodyString + " ");
        sb.append(PREFIX_START + task.getStartDateTime().dateTimeString + " ");
        sb.append(PREFIX_END + task.getEndDateTime().dateTimeString + " ");
        sb.append(PREFIX_PRIORITY + task.getPriority().priorityString + " ");
        task.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UpdateTaskDescriptor}'s details.
     */
    /*
    public static String getUpdateTaskDescriptorDetails(UpdateTaskCommand.UpdateTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTaskName().ifPresent(taskName -> sb.append(PREFIX_NAME).append(taskName.fullName).append(" "));
        descriptor.getBody().isPresent(body -> sb.append(PREFIX_BODY).append(body.bodyString).append(" "));
        descriptor.getStartDateTime().isPresent(startDateTime -> sb.append(PREFIX_START)
                .append(startDateTime.dateTimeString).append(" "));
        descriptor.getEndDateTime().isPresent(endDateTime -> sb.append(PREFIX_END)
                .append(endDateTime.dateTimeString).append(" "));
        descriptor.getPriority().isPresent(priority -> sb.append(PREFIX_PRIORITY)
                .append(priority.priorityString).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }*/
}
