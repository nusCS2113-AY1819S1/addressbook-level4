package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.TimeSlot;

/** TODO ALEXIS: conversion into a timeSlot (visually a square in the TimetableGrid)
 * An UI component that displays information of a {@code Person}.
 */
public class TimeTablePanelTimeSlot extends UiPart<Region> {

    private static final String FXML = "TimeTablePanelTimeSlot.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final TimeSlot timeSlot;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name; //name of timeSlot
    @FXML
    private Label id; //index of timeSlot in current TimeTableGrid
    //@FXML
    //private FlowPane tags; //TODO ALEXIS: tag for this timeSlot (necessary or not? )

    public TimeTablePanelTimeSlot(TimeSlot timeSlot, int displayedIndex) {
        super(FXML);
        this.timeSlot = timeSlot;
        id.setText(displayedIndex + ". ");
        //TODO ALEXIS: pls verify the below line cannot be further OOP-fied/ optimised
        name.setText(timeSlot.getModName());
        //TODO ALEXIS: create public getTags function in getName
        //timeSlot.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimeTablePanelTimeSlot)) {
            return false;
        }

        // state check
        TimeTablePanelTimeSlot card = (TimeTablePanelTimeSlot) other;
        return id.getText().equals(card.id.getText())
                && timeSlot.equals(card.timeSlot); // TODO ALEXIS: pls verify this whole function makes sense.
    }
}
