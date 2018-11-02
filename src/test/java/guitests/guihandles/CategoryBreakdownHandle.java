package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * A handle for the GUI component CategoryBreakdown
 */
public class CategoryBreakdownHandle extends NodeHandle<Node> {

    public static final String CATEGORY_BREAKDOWN_HANDLE = "categoryBreakdown";

    private AnchorPane root;

    public CategoryBreakdownHandle(AnchorPane node) {
        super(node);
        root = node;
    }
}
