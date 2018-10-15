package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {
    public static final String DEFAULT_GROUP_NAME = "TUT[10]";
    public static final String DEFAULT_GROUP_LOCATION = "E10-10-10";

    private GroupName groupName;
    private GroupLocation groupLocation;
    private Set<Tag> tags;
    private Set<Person> persons;

    public GroupBuilder() {
        groupName = new GroupName(DEFAULT_GROUP_NAME);
        groupLocation = new GroupLocation(DEFAULT_GROUP_LOCATION);
        tags = new HashSet<>();
        persons = new HashSet<>();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        groupName = groupToCopy.getGroupName();
        groupLocation = groupToCopy.getGroupLocation();
        tags = new HashSet<>(groupToCopy.getTags());
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    /**
     * Sets the {@code GroupLocation} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupLocation(String groupLocation) {
        this.groupLocation = new GroupLocation(groupLocation);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withPersons(Person ... persons) {
        this.persons = SampleDataUtil.getPersonSet(persons);
        return this;
    }

    public Group build() {
        return new Group(groupName, groupLocation, tags);
    }

}
