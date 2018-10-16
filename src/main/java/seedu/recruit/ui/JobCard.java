package seedu.recruit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * An UI component that displays the name of the job,
 * required gender, age range, salary and education level
 * of a job in {@code Company}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CandidateBook level 4</a>
     */

    public final JobOffer jobOffer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label job;
    @FXML
    private Label gender;
    @FXML
    private Label ageRange;
    @FXML
    private Label salary;
    @FXML
    private Label education;


    public JobCard(JobOffer jobOffer, int displayedIndex) {
        super(FXML);
        this.jobOffer = jobOffer;
        id.setText(displayedIndex + ". ");
        job.setText(jobOffer.getJob().value);
        gender.setText(jobOffer.getGender().value);
        ageRange.setText(jobOffer.getAgeRange().value);
        salary.setText(jobOffer.getSalary().value);
        education.setText(jobOffer.getEducation().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCard)) {
            return false;
        }

        // state check
        JobCard card = (JobCard) other;
        return id.getText().equals(card.id.getText())
                && jobOffer.equals(card.jobOffer);
    }
}
