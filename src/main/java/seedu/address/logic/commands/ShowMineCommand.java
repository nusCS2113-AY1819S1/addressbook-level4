package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.event.AttendeeContainsEmailPredicate;

//@@author: IcedCoffeeBoy

/**
 * List only events associated with the login user
 */
public class ShowMineCommand extends Command {

    public static final String COMMAND_WORD = "showmine";

    public static final String MESSAGE_SUCCESS = "Sucessfuly show the events associated with you"
            + "\nTo view all persons and events please use list all command"
            + "\nExample: list all";

    private String loginIdentity;


    public ShowMineCommand(String loginIdentity) {
        this.loginIdentity = loginIdentity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        AttendeeContainsEmailPredicate predicateNoDateFilter = new AttendeeContainsEmailPredicate(loginIdentity);
        model.updateFilteredEventList(predicateNoDateFilter);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowMineCommand
                && ((loginIdentity == null && ((ShowMineCommand) other).loginIdentity == null)
                || loginIdentity.equals(((ShowMineCommand) other).loginIdentity)));
    }
}
