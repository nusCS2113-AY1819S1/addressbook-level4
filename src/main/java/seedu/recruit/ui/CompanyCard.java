package seedu.recruit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recruit.model.company.Company;

/**
 * An UI component that displays the
 * level of education, desired job and desired salary
 * of a {@code Candidate}.
 */
public class CompanyCard extends UiPart<Region> {

    private static final String FXML = "CompanyCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CandidateBook level 4</a>
     */

    public final Company company;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;


    public CompanyCard(Company company, int displayedIndex) {
        super(FXML);
        this.company = company;
        id.setText(displayedIndex + ". ");
        name.setText(company.getCompanyName().value);
        phone.setText(company.getPhone().value);
        email.setText(company.getEmail().value);
        address.setText(company.getAddress().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyCard)) {
            return false;
        }

        // state check
        CompanyCard card = (CompanyCard) other;
        return id.getText().equals(card.id.getText())
                && company.equals(card.company);
    }
}
