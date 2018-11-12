package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label employeeId;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label department;
    @FXML
    private Label position;
    @FXML
    private Label salary;
    @FXML
    private Label bonus;
    @FXML
    private Label employeeIdLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label dateOfBirthLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label bonusLabel;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        employeeIdLabel.setText("Employee ID: ");
        employeeId.setText(person.getEmployeeId().value);
        phoneLabel.setText("Phone: ");
        phone.setText(person.getPhone().value);
        addressLabel.setText("Address: ");
        address.setText(person.getAddress().value);
        emailLabel.setText("Email: ");
        email.setText(person.getEmail().value);
        dateOfBirthLabel.setText("Date Of Birth: ");
        dateOfBirth.setText(person.getDateOfBirth().value);
        departmentLabel.setText("Department: ");
        department.setText(person.getDepartment().value);
        positionLabel.setText("Position: ");
        position.setText(person.getPosition().value);
        salaryLabel.setText("Salary: ");
        salary.setText(person.getSalary().value);
        bonusLabel.setText("Bonus: ");
        bonus.setText(person.getBonus().value);
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
