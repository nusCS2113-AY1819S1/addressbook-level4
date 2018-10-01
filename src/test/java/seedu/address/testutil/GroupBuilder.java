package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUP_NAME = "TUT[1]";
    public static final String DEFAULT_GROUP_LOCATION = "COM1-B103";

    private GroupName groupName;
    private GroupLocation groupLocation;
    private Set<Tag> tags;

    public GroupBuilder() {
        groupName = new GroupName(DEFAULT_GROUP_NAME);
        groupLocation = new GroupLocation(DEFAULT_GROUP_LOCATION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        groupName = groupToCopy.getGroupName();
        groupLocation = groupToCopy.getGroupLocation();
        tags = new HashSet<>(groupToCopy.getTags());
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    /**
     * Sets the {@code GroupLocation} of the {@code Group} that we are building.
     */
    public GroupBuilder withLocation(String groupLocation) {
        this.groupLocation = new GroupLocation(groupLocation);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public GroupBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Group build() {
        return new Group(groupName, groupLocation, tags);
    }

}
