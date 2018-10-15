package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;

/**
 * Provides a handle to a group card in the person list panel.
 */
public class GroupCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String GROUP_NAME_FIELD_ID = "#groupName";
    private static final String GROUP_LOCATION_FIELD_ID = "#groupLocation";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label groupNameLabel;
    private final Label groupLocationLabel;
    private final List<Label> tagLabels;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        groupNameLabel = getChildNode(GROUP_NAME_FIELD_ID);
        groupLocationLabel = getChildNode(GROUP_LOCATION_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getGroupName() {
        return groupNameLabel.getText();
    }

    public String getGroupLocation() {
        return groupLocationLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    /**
     * Returns true if this handle contains {@code group}.
     */
    public boolean equals(Group group) {
        return getGroupName().equals(group.getGroupName().groupName)
                && getGroupLocation().equals(group.getGroupLocation().groupLocation)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(group.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
