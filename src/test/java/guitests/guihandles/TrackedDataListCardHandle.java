package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.workout.Parameter;

/**
 * Provides a handle to a tracked data list card in the tracked data list panel.
 */
public class TrackedDataListCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PREFIX_FIELD_ID = "#prefix";
    private static final String VALUE_FIELD_ID = "#value";

    private final Label idLabel;
    private final Label prefixLabel;
    private final Label valueLabel;

    public TrackedDataListCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        prefixLabel = getChildNode(PREFIX_FIELD_ID);
        valueLabel = getChildNode(VALUE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPrefix() {
        return prefixLabel.getText();
    }

    public String getValue() {
        return valueLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code parameter}.
     */
    public boolean equals(Parameter parameter) {
        return getPrefix().equals(parameter.getPrefix().getPrefix())
                && getValue().equals(parameter.getValue());
    }
}
