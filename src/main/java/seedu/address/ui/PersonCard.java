package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES = {"teal", "red", "yellow", "blue", "orange", "brown", "green",
            "pink", "black", "grey", "maroon", "navy"};


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
    private FlowPane enrolledClasses;
    @FXML
    private FlowPane header;
    @FXML
    private FlowPane monday;
    @FXML
    private FlowPane tuesday;
    @FXML
    private FlowPane wednesday;
    @FXML
    private FlowPane thursday;
    @FXML
    private FlowPane friday;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

       /* for(String tempEnrolledClassName : person.getEnrolledClasses().keySet()){
            Label enrolledClass = new Label(tempEnrolledClassName);
            enrolledClass.setPrefSize(61, 10);
            header.getChildren().add(enrolledClass);
        }*/

        for (String it : Tag.getHeader()) {
            Label day = new Label(it);
            day.setPrefSize(58, 10);
            header.getChildren().add(day);

        }
        String[] mon = new String[12];
        String[] tue = new String[12];
        String[] wed = new String[12];
        String[] thu = new String[12];
        String[] fri = new String[12];

        for (int i = 0; i < 12; i++) {
            mon[i] = person.getTimeSlots().get("mon").get(i).toString();
        }
        for (int i = 0; i < 12; i++) {
            tue[i] = person.getTimeSlots().get("tue").get(i).toString();
        }
        for (int i = 0; i < 12; i++) {
            wed[i] = person.getTimeSlots().get("wed").get(i).toString();
        }
        for (int i = 0; i < 12; i++) {
            thu[i] = person.getTimeSlots().get("thu").get(i).toString();
        }
        for (int i = 0; i < 12; i++) {
            fri[i] = person.getTimeSlots().get("fri").get(i).toString();
        }
        getMod(mon, monday);
        getMod(tue, tuesday);
        getMod(wed, wednesday);
        getMod(thu, thursday);
        getMod(fri, friday);


    }

    //Takes the mods for the day and adds them to the FlowPane
    private void getMod(String[] mods, FlowPane day) {
        for (String it : mods) {
            Label slot = new Label(it);
            slot.setPrefSize(51, 25);
            if (it.length() < 6) {
                slot.getStyleClass().add(getColor(it));
            } else if (it.equalsIgnoreCase("busy")) {
                slot.getStyleClass().add("black");
                slot.setText(" ");
            } else if (it.equalsIgnoreCase("free")) {
                slot.getStyleClass().add("white");
                slot.setText(" ");
            } else if (it.charAt(5) == 'm' || it.charAt(5) == 'a') {
                slot.getStyleClass().add("white");
                slot.setText(" ");

            } else {
                slot.getStyleClass().add(getColor(it));
            }


            day.getChildren().add(slot);
        }
    }

    //Returns a colour based on the module code
    public static String getColor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Reads tags from a person, changes them to labels and adds colour
     */
    private void colourTag(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equals("free")) {
                tagLabel.getStyleClass().add("white");
                tagLabel.setText(" ");

            } else if (tag.tagName.equals("busy")) {
                tagLabel.getStyleClass().add("black");
                tagLabel.setText(" ");
            } else {
                tagLabel.getStyleClass().add(getColor(tag.tagName));
            }
            tagLabel.setPrefSize(53, 25);
            monday.getChildren().add(tagLabel);


        });
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
