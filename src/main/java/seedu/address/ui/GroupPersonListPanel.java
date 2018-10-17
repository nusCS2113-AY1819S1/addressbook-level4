package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;


/**
 * Panel containing the list of persons in groups.
 */
public class GroupPersonListPanel extends UiPart<Region> {
    private static final String FXML = "GroupPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupPersonListPanel.class);

    @FXML
    private ListView<Person> groupPersonListView;

    public GroupPersonListPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void loadGroupPersons(Group group){
        ObservableList<Person> personObservableList = FXCollections.observableArrayList();
        for (Person p: group.getPersons()) {
            personObservableList.add(p);
        }

        groupPersonListView.setItems(personObservableList);
        groupPersonListView.setCellFactory(listView -> new PersonListViewCell());
    }

    @Subscribe
    private void handleGroupPanelSelectionChangedEvent(GroupPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGroupPersons(event.getNewSelection());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
