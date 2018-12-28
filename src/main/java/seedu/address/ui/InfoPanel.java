package seedu.address.ui;

import static seedu.address.ui.PlaceholderUtil.DATA_PROTECTION_NOTICE_TEXT;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * The Info Panel of the App.
 */
public class InfoPanel extends UiPart<Region> {
    private static final String FXML = "InfoPanel.fxml";
    private Person person;

    @FXML
    private ScrollPane infoScrollPane;
    @FXML
    private AnchorPane additionalInfo;
    @FXML
    private AnchorPane medHistory;
    @FXML
    private AnchorPane medAppts;
    @FXML
    private AnchorPane medReports;

    @FXML
    private Label nameLabel1;
    @FXML
    private Label nameLabel2;
    @FXML
    private Label nameLabel3;
    @FXML
    private Label nameLabel4;

    // Additional Info Pane
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label dateOfBirthLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label nricLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label bloodTypeLabel;
    @FXML
    private Label occupationLabel;
    @FXML
    private Label maritalStatusLabel;

    @FXML
    private Label fullNameTag;
    @FXML
    private Label nricTag;
    @FXML
    private Label dateOfBirthTag;
    @FXML
    private Label addressTag;
    @FXML
    private Label phoneTag;
    @FXML
    private Label heightTag;
    @FXML
    private Label weightTag;
    @FXML
    private Label genderTag;
    @FXML
    private Label emailTag;
    @FXML
    private Label bloodTypeTag;
    @FXML
    private Label occupationTag;
    @FXML
    private Label maritalStatusTag;

    @FXML
    private VBox medHistoryVBox;
    @FXML
    private VBox apptsVBox;
    @FXML
    private VBox medReportsVBox;

    public InfoPanel() {
        super(FXML);
    }

    public void setPerson(Person person) {
        this.person = person;

        initialiseNameLabels();
        fillAdditionalInfoPane();
        fillMedHistoriesPane();
        fillApptsPane();
        fillMedReportsPane();
    }

    /**
     * Initialises the name labels in all anchor panes
     */
    private void initialiseNameLabels() {
        nameLabel1.setText("Additional Info for " + person.getName());
        nameLabel2.setText("Medical History for " + person.getName());
        nameLabel3.setText("Appt Schedule for " + person.getName());
        nameLabel4.setText("Medical Reports for " + person.getName());
    }

    /**
     * Initialize Additional Information labels
     */
    private void fillAdditionalInfoPane() {
        additionalInfo.setStyle("-fx-background-color: #FCFCFC");

        fullNameTag.setText("Full Name:");
        nricTag.setText("NRIC:");
        dateOfBirthTag.setText("Date of Birth:");
        addressTag.setText("Address:");
        phoneTag.setText("Phone:");
        heightTag.setText("Height:");
        weightTag.setText("Weight:");
        genderTag.setText("Gender:");
        emailTag.setText("Email:");
        bloodTypeTag.setText("Blood Type:");
        occupationTag.setText("Occupation:");
        maritalStatusTag.setText("Marital Status:");

        fullNameLabel.setText(person.getName().fullName);
        emailLabel.setText(person.getEmail().value);
        nricLabel.setText(person.getNric().value);
        dateOfBirthLabel.setText(person.getDateOfBirth().toString() + "\t" + person.getDateOfBirth().ageToString());
        addressLabel.setText(person.getAddress().value);
        phoneLabel.setText(person.getPhone().value);
        heightLabel.setText(person.getHeight().value);
        weightLabel.setText(person.getWeight().value);
        genderLabel.setText(person.getGender().toString());
        bloodTypeLabel.setText(person.getBloodType().value);
        occupationLabel.setText(person.getOccupation().value);
        maritalStatusLabel.setText(person.getMaritalStatus().toString());
    }

    /**
     * Initialize Medical History Flow Pane
     */
    private void fillMedHistoriesPane() {
        medHistory.setStyle("-fx-background-color: #ECECEC");
        person.getMedHistory().forEach(medHistory ->
                medHistoryVBox.getChildren().add(new MedHistoryCard(medHistory)));
    }

    /**
     * Initialize Medical Appointment Flow Pane
     */
    private void fillApptsPane() {
        medAppts.setStyle("-fx-background-color: #DCDCDC");
        person.getAppts().forEach(appt -> apptsVBox.getChildren().add(new ApptCard(appt)));
    }

    /**
     * Initialize Medical Report Flow Pane
     */
    private void fillMedReportsPane() {
        medReports.setStyle("-fx-background-color: #CCCCCC");
        person.getMedicalReports().forEach(report ->
                medReportsVBox.getChildren().add(new ReportCard(report)));
    }

    /**
     * Empties vboxes in all anchorpanes
     */
    public void clearVBoxes() {
        medHistoryVBox.getChildren().clear();
        medReportsVBox.getChildren().clear();
        apptsVBox.getChildren().clear();
    }

    /**
     * Shows a message when there is no person card selected to tell the user to select a person
     */
    public void showEmptyMessage() {
        additionalInfo.setStyle("-fx-background-color: #FFFFFF");
        medHistory.setStyle("-fx-background-color: #FFFFFF");
        medAppts.setStyle("-fx-background-color: #FFFFFF");
        medReports.setStyle("-fx-background-color: #FFFFFF");
        nameLabel1.setText("You have not selected any person.\n"
                + "Please select a person to show more details\nfor him/her on this panel.");
        nameLabel2.setText(DATA_PROTECTION_NOTICE_TEXT);
        nameLabel3.setText("");
        nameLabel4.setText("");

        fullNameTag.setText("");
        nricTag.setText("");
        dateOfBirthTag.setText("");
        addressTag.setText("");
        phoneTag.setText("");
        heightTag.setText("");
        weightTag.setText("");
        genderTag.setText("");
        emailTag.setText("");
        bloodTypeTag.setText("");
        occupationTag.setText("");
        maritalStatusTag.setText("");

        fullNameLabel.setText("");
        emailLabel.setText("");
        nricLabel.setText("");
        dateOfBirthLabel.setText("");
        addressLabel.setText("");
        phoneLabel.setText("");
        heightLabel.setText("");
        weightLabel.setText("");
        genderLabel.setText("");
        bloodTypeLabel.setText("");
        occupationLabel.setText("");
        maritalStatusLabel.setText("");
    }
}
