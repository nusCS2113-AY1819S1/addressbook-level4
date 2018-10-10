package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * The UI Component to the Attendance List.
 */
public class DisplayGrade extends UiPart<Stage> {

    private static final String FXML = "DisplayGrade.fxml";
    private ObservableList<Person> persons;

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> gradeColumn;


    /**
     * Create list to display person and grades.
     */
    public DisplayGrade(ArrayList<Person> persons) {
        this(new Stage());
        this.persons = FXCollections.observableArrayList(persons); //convert arraylist to observableArraylist
    }


    /**
     * Setup the Stage for grade List.
     */
    public DisplayGrade(Stage newStage) {
        super(FXML, newStage);
        newStage.setMaximized(true);
        newStage.show();
        registerAsAnEventHandler(this);
    }

    /**
     * Using ObservableList to generate the grade List.
     */
    @FXML
    public void generateGradeList() {
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Initialize the person table with all the data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        personTable.setItems(persons);

    }

}
