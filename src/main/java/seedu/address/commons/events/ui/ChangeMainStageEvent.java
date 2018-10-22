package seedu.address.commons.events.ui;

import javafx.stage.Stage;
import seedu.address.commons.events.BaseEvent;

public class ChangeMainStageEvent extends BaseEvent {

    public final Stage mainStage;

    public ChangeMainStageEvent (Stage stage){
        this.mainStage = stage;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
