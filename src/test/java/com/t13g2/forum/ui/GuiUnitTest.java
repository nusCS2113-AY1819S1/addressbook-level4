package com.t13g2.forum.ui;

import java.util.Optional;

import org.junit.After;
import org.junit.Rule;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.ui.testutil.UiPartRule;
import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;

import javafx.scene.Node;

/**
 * A GUI unit test class for ForumBook.
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
