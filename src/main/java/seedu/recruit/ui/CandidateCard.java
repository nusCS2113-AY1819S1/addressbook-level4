package seedu.recruit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recruit.model.candidate.Candidate;

/**
 * An UI component that displays the name and the
 * level of education, desired job and desired salary
 * of a {@code Candidate}.
 */
public class CandidateCard extends UiPart<Region> {

    private static final String FXML = "CandidateCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CandidateBook level 4</a>
     */

    public final Candidate candidate;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label age;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label desired_job;
    @FXML
    private Label education;
    @FXML
    private Label salary;
    @FXML
    private FlowPane tags;

    public CandidateCard(Candidate candidate, int displayedIndex) {
        super(FXML);
        this.candidate = candidate;
        id.setText(displayedIndex + ". ");
        name.setText(candidate.getName().fullName);
        desired_job.setText(candidate.getJob().value);
        education.setText(candidate.getEducation().value);
        salary.setText(candidate.getSalary().value);
        candidate.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CandidateCard)) {
            return false;
        }

        // state check
        CandidateCard card = (CandidateCard) other;
        return id.getText().equals(card.id.getText())
                && candidate.equals(card.candidate);
    }
}
