package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel containing just the current User
 */
public class MePanel extends UiPart<Region> {

    private static final String FXML = "MePanel.fxml";

    @FXML
    private ListView<Person> meView;

    public MePanel(ObservableList<Person> personList) {
        super(FXML);
        setConnections(personList);
    }

    private void setConnections(ObservableList<Person> personList) {
        meView.setItems(personList);
        meView.setCellFactory(listView -> new MePanel.MeViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MeViewCell extends ListCell<Person> {
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
