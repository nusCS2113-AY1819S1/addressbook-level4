package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.recruitment.Recruitment;

/**
 * An UI component that displays information of a {@code Recruitment}.
 */
public class RecruitmentCard extends UiPart<Region> {

    private static final String FXML = "RecruitmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Recruitment recruitment;

    @FXML
    private Label id;
    @FXML
    private Label post;
    @FXML
    private Label workExp;
    @FXML
    private Label workExpLabel;
    @FXML
    private Label jobDescription;

    public RecruitmentCard (Recruitment recruitment, int displayedIndex) {
        super(FXML);
        this.recruitment = recruitment;
        id.setText(displayedIndex + ". ");
        post.setText(recruitment.getPost().value);
        workExpLabel.setText("Min Work Exp:");
        workExp.setText(recruitment.getWorkExp().workExp + " years");
        jobDescription.setText(recruitment.getJobDescription().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecruitmentCard)) {
            return false;
        }

        // state check
        RecruitmentCard card = (RecruitmentCard) other;
        return id.getText().equals(card.id.getText())
                && recruitment.equals(card.recruitment);
    }
}
