package seedu.address.model.task;

/**
 * Represents a Task's datails in the task list .
 * Guarantees: immutable.
 */
public class Body {

    public static final String MESSAGE_BODY_CONSTRAINTS =
            "Task body can take any values, and it should not be blank";

    private String bodyString;

    public Body(String bodyString) {
        this.bodyString = bodyString;
    }

    @Override
    public String toString() {
        return bodyString;
    }
}
