package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITMENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;

/**
 * Reverts the {@code model}'s address book, schedule list, expenses list, recruitment list
 * to its previously undone state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous command.";

    /**
     * UndoCommand execution.
     * @see seedu.address.model.VersionedModelList class for the tracking of storage commits across
     * all storage types (addressbook, expensesList, scheduleList, recruitmentList)
     * <p>
     *      Get the last commit type from {@code VersionedModelList} class, which is a set
     *      containing which storage has been committed. Hence, the same storage
     *      will be allowed to perform undo.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoModel()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        Set<ModelTypes> myModelUndoSet = model.getLastCommitType();

        for (ModelTypes myModel : myModelUndoSet) {

            switch(myModel) {
            case SCHEDULES_LIST:
                if (model.canUndoScheduleList()) {
                    model.undoScheduleList();
                    model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                }
                break;

            case EXPENSES_LIST:
                if (model.canUndoExpensesList()) {
                    model.undoExpensesList();
                    model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
                }
                break;

            case RECRUITMENT_LIST:
                if (model.canUndoRecruitmentList()) {
                    model.undoRecruitmentList();
                    model.updateFilteredRecruitmentList(PREDICATE_SHOW_ALL_RECRUITMENT);
                }
                break;

            case ADDRESS_BOOK:
                if (model.canUndoAddressBook()) {
                    model.undoAddressBook();
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                }
                break;

            default:
                break;
            }
        }

        model.undoModelList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
