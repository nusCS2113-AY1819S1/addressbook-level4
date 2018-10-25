package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.todo.Todo;;

/**
 * A utility class containing a list of {@code Todo} objects to be used in tests.
 */
public class TypicalTodos {
    public static final Todo TASK1 = new TodoBuilder().withTitle("Buy dog food")
            .withContent("EAT you own dog food please").build();

    public static final Todo TASK2 = new TodoBuilder().withTitle("Buy dog food")
    .withContent("EAT you own dog food please").build();

    public static final String KEYWORD_MATCHING_TASK2 = "TASK2"; // A keyword that matches TASK2

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
        return new ArrayList<>(Arrays.asList(TASK1, TASK2));
    }
}
