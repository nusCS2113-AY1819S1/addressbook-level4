package UnRefactored.ui;

import java.util.Optional;

import org.junit.After;
import org.junit.Rule;

import UnRefactored.guitests.GuiRobot;
import UnRefactored.guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import UnRefactored.commons.core.EventsCenter;
import UnRefactored.ui.testutil.UiPartRule;

/**
 * A GUI unit test class for AddressBook.
 */
public abstract class GuiUnitTest {
    @Rule
    public final UiPartRule uiPartRule = new UiPartRule();

    protected final GuiRobot guiRobot = new GuiRobot();

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector of the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <T extends Node> T getChildNode(Node rootNode, String query) {
        Optional<T> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }
}
