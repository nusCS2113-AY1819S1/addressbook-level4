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
    private static final String[] TAG_COLOR_STYLES = { "teal", "red", "yellow", "blue", "orange", "brown", "green",
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

        for (String it: Tag.getHeader())
        {
            Label day = new Label(it);
            day.setPrefSize(61, 10);
            header.getChildren().add(day);

        }
        colourTag(person);
        getMod(Tag.getTue(), tuesday);
        getMod(Tag.getWed(), wednesday);
        getMod(Tag.getThu(), thursday);
        getMod(Tag.getFri(), friday);



    }
    //Takes the mods for the day and adds them to the FlowPane
    private void getMod(String mods[], FlowPane day) {
        for (String it : mods) {
            Label slot = new Label(it);
            slot.setPrefSize(53, 25);
            if(it.charAt(5)=='m'||it.charAt(5)=='a'){
                slot.getStyleClass().add("white");
                slot.setText(" ");

            }
            else {
                slot.getStyleClass().add(getColor(it));
            }


            day.getChildren().add(slot);
        }
    }

    //Returns a colour based on the module code
    public static String getColor(String tagName){
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }
    //Reads tags from a person, changes them to labels and adds colour
    private void colourTag(Person person){
        person.getTags().forEach(tag->{
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getColor(tag.tagName));
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
