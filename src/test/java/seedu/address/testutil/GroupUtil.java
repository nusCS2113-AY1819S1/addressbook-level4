package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.model.group.Group;

/**
 * A utility class for Person.
 */
public class GroupUtil {
    /**
     * Returns an sreate group command string for adding the {@code group}.
     */
    public static String getCreateGroupCommand(Group group) {
        return CreateGroupCommand.COMMAND_WORD + " " + getGroupDetails(group);
    }

    /**
     * Returns the part of command string for the given {@code group}'s details.
     */
    public static String getGroupDetails(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + group.getGroupName().groupName + " ");
        sb.append(PREFIX_GROUP_LOCATION + group.getGroupLocation().value + " ");
        group.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
