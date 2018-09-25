package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

public class AttendanceStage extends UiPart<Stage>  {

    private static final String stageName = "Attendance List";
    private static final String FXML = "AttendanceStage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage secondaryStage;

    private ObservableList<Person> Persons;

    @FXML
    private TableView<Person> PersonTable;

    @FXML
    private TableColumn<Person,String> nameColumn;

    @FXML
    private TableColumn<Person,String> phoneColumn;

    @FXML
    private TableColumn<Person,String> addressColumn;

    @FXML
    private TableColumn<Person,String> EmailColumn;


    /**
     * Create new Stage for AttendanceList.
     */
    public AttendanceStage(ObservableList<Person> Persons){
        this(new Stage());
        this.Persons=Persons;
    }


    /**
     * Setup the Stage for AttendanceList.
     */
    public AttendanceStage(Stage newStage){
        super(FXML,newStage);
        this.secondaryStage=newStage;
        newStage.setHeight(1000);
        newStage.setWidth(1000);
        newStage.show();
        registerAsAnEventHandler(this);
    }

    /**
     * Using ObservableList to generate the Attendance List.
     */
    @FXML
    public void generateAttendance() {
        // Initialize the person table with all the data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        PersonTable.setItems(Persons);
    }

    public void printAttendanceList(){
        PrinterJob job=PrinterJob.createPrinterJob();
        if(job != null){
            job.showPrintDialog(secondaryStage); // Window must be your main Stage
            job.printPage(PersonTable);
            job.endJob();
        }
    }

}
