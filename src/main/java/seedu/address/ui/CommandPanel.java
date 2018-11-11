package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Role;
import seedu.address.logic.commands.CommandSyntax;



/**
 * The Command Panel of the App.
 */
public class CommandPanel extends UiPart<Region> {
    private static final String FXML = "CommandPanel.fxml";
    private CommandBox commandBox;
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ListView<String> commandPanel;

    public CommandPanel(CommandBox commandBox) {
        super(FXML);
        commandPanel.setItems(FXCollections.observableArrayList(Role.getLegalCommands()));
        this.commandBox = commandBox;
    }
    /**
     * Provides the message usage for the command word and fills in the command box with the syntax
     * upon clicking of item on the list view.
     */
    @FXML
    private void mouseClicked() {
        String item = commandPanel.getSelectionModel().getSelectedItem();
        commandBox.replaceText(CommandSyntax.getSyntax(item));
        commandBox.getRoot().requestFocus();
        raise(new NewResultAvailableEvent(CommandSyntax.getMessage(item)));
    }
}
