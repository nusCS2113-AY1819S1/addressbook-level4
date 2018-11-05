package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seedu.address.model.grade.PersonTest;

/**
 * The UI Component to the Attendance List.
 */
public class DisplayGrade extends UiPart<Stage> {

    private static final String FXML = "DisplayGrade.fxml";
    private ObservableList<PersonTest> persons;

    @FXML
    private TableView<PersonTest> personTable;


    @FXML
    private TableColumn<PersonTest, String> nameColumn;

    @FXML
    private TableColumn<PersonTest, String> marksColumn;

    @FXML
    private TableColumn<PersonTest, String> testNameColumn;


    /**
     * Create list to display person and grades.
     */
    public DisplayGrade(ArrayList<PersonTest> persons) {
        this(new Stage());
        this.persons = FXCollections.observableArrayList(persons); //convert arraylist to observable Arraylist
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
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        testNameColumn.setCellValueFactory(new PropertyValueFactory<>("testName"));
        personTable.setItems(persons);

    }

}





