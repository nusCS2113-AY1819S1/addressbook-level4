package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import javafx.scene.layout.StackPane;
import seedu.address.model.medicalreport.MedicalReport;

//@@author chewkahmeng
/**
 * An UI component that displays information of a {@code Medical Report}.
 */
public class ReportCard extends StackPane {

    @FXML
    private Label reportDetails;
    @FXML
    private Label reportInfo;

    public ReportCard(MedicalReport report) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/view/ReportCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        reportDetails.setText("Title: " + report.getTitle().toString() + "\n" + "Date: " + report.getDate().toString());
        reportInfo.setText("Information: " + report.getInformation().toString());
    }
}
