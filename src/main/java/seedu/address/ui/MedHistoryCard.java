package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.DischargeStatus;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;

//@@author xShaivan
/**
 * An UI component that displays information of a {@code MedHistory}.
 */
public class MedHistoryCard extends StackPane {

    @FXML
    private Label dateDischargeCode;
    @FXML
    private Label otherMedHistoryInfo;

    public MedHistoryCard(MedHistory medHistory) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/view/MedHistoryCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        dateDischargeCode.setText(medHistory.getMedHistDate().toString() + " "
                + medHistory.getDischargeStatus().orElse(new DischargeStatus("")).toString() + "\n");
        otherMedHistoryInfo.setText("Allergy: " + medHistory.getAllergy().orElse(new Allergy("")).toString() + "\n"
                + "Previous Country Visited: " + medHistory.getPrevCountry().orElse(new PrevCountry("")).toString());
    }
}
