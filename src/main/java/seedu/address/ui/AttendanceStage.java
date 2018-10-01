package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import seedu.address.model.person.Person;

/**
 * The UI Component to the Attendance List.
 */
public class AttendanceStage extends UiPart<Stage> {

    private static final String FXML = "AttendanceStage.fxml";

    //private Stage secondaryStage;

    private ObservableList<Person> persons;

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> phoneColumn;

    @FXML
    private TableColumn<Person, String> addressColumn;

    @FXML
    private TableColumn<Person, String> emailColumn;


    /**
     * Create new Stage for AttendanceList.
     */
    public AttendanceStage(ObservableList<Person> persons) {
        this(new Stage());
        this.persons = persons;
    }


    /**
     * Setup the Stage for AttendanceList.
     */
    public AttendanceStage(Stage newStage) {
        super(FXML, newStage);
        //secondaryStage = newStage;
        newStage.setMaximized(true);
        newStage.show();
        registerAsAnEventHandler(this);
    }

    /**
     * Using ObservableList to generate the Attendance List.
     */
    @FXML
    public void generateAttendance() {
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Initialize the person table with all the data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        personTable.setItems(persons);
    }

    /**
     *
     * Resize the TableView to fit A4 Size Paper for printing
     * https://stackoverflow.com/questions/31231021/javafx8-print-api-how-to-set-correctly-the-printable-area
     */

    public void printResizedTable() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(
                Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        PrinterJob job = PrinterJob.createPrinterJob();

        double scaleX = pageLayout.getPrintableWidth() / personTable.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / personTable.getBoundsInParent().getHeight();

        Scale scale = new Scale(scaleX, scaleY);

        personTable.getTransforms().add(scale);

        if (job != null && job.showPrintDialog(personTable.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, personTable);
            if (success) {
                job.endJob();
            }
        }
        personTable.getTransforms().remove(scale);
    }
}
