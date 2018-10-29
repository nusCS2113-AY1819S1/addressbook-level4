package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TASK2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.todo.Todo;

//@@author linnnruoo
/**
 * A utility class containing a list of {@code Todo} objects to be used in tests.
 */
public class TypicalTodos {
    public static final Todo TASKA = new TodoBuilder().withTitle("Buy dog food")
            .withContent("EAT you own dog food please").build();
    public static final Todo TASKB = new TodoBuilder().withTitle("Buy dog food")
            .withContent("EAT you own dog food please").build();

    // Manually added - todo task's details found in {@code CommandTestUtil}
    public static final Todo TASK1 = new TodoBuilder().withTitle(VALID_TITLE_TASK1)
            .withContent(VALID_CONTENT_TASK1).build();
    public static final Todo TASK2 = new TodoBuilder().withTitle(VALID_TITLE_TASK2)
            .withContent(VALID_CONTENT_TASK2).build();

    public static final String KEYWORD_MATCHING_TASKC = "TASKC"; // A keyword that matches TASK2

    private TypicalTodos() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical todo tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Todo todoTask : getTypicalTodos()) {
            ab.addTodo(todoTask);
        }
        return ab;
    }

    public static List<Todo> getTypicalTodos() {
        return new ArrayList<>(Arrays.asList(TASKA, TASKB));
    }
}
