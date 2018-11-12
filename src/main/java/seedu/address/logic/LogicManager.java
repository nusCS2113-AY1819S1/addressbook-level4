package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearExpenseCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteExpenseCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditExpenseCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpenseTrendCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTaskCommand;
import seedu.address.logic.commands.MonthlyExpenseCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RedoExpenseCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoExpenseCommand;
import seedu.address.logic.commands.UpdateTaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;


/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@@author ian-tjahjono
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    //@@author

    //@@author ChenSongJian
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenseList();
    }

    //@@author luhan02
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    //@@author

    @Override
    public ArrayList<String> getCommandList() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME + " "
                + PREFIX_PHONE + " "
                + PREFIX_EMAIL + " "
                + PREFIX_ADDRESS + " "
                + PREFIX_TAG);
        commandList.add(AddExpenseCommand.COMMAND_WORD);
        commandList.add(AddExpenseCommand.COMMAND_WORD + " "
                + PREFIX_EXPENSE_CATEGORY + " "
                + PREFIX_EXPENSE_VALUE + " "
                + PREFIX_EXPENSE_DATE + " "
                + PREFIX_TAG);
        commandList.add(AddTaskCommand.COMMAND_WORD);
        commandList.add(AddTaskCommand.COMMAND_WORD + " "
                + PREFIX_NAME + " "
                + PREFIX_BODY + " "
                + PREFIX_START + " "
                + PREFIX_END + " "
                + PREFIX_PRIORITY + " "
                + PREFIX_TAG);
        commandList.add(BackupCommand.COMMAND_WORD);
        commandList.add(BackupCommand.COMMAND_WORD + " [SERVICE AUTHTOKEN]");
        commandList.add(RestoreCommand.COMMAND_WORD);
        commandList.add(RestoreCommand.COMMAND_WORD + " [SERVICE]");
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ClearExpenseCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD + " INDEX");
        commandList.add(DeleteExpenseCommand.COMMAND_WORD);
        commandList.add(DeleteExpenseCommand.COMMAND_WORD + " INDEX");
        commandList.add(DeleteTaskCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD + " INDEX "
                + PREFIX_NAME + " "
                + PREFIX_PHONE + " "
                + PREFIX_EMAIL + " "
                + PREFIX_ADDRESS + " "
                + PREFIX_TAG);
        commandList.add(EditExpenseCommand.COMMAND_WORD);
        commandList.add(EditExpenseCommand.COMMAND_WORD + " INDEX "
                + PREFIX_EXPENSE_CATEGORY + " "
                + PREFIX_EXPENSE_DATE + " "
                + PREFIX_EXPENSE_VALUE + " "
                + PREFIX_TAG);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(ExpenseTrendCommand.COMMAND_WORD);
        commandList.add(FindAddressCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD + " KEYWORD");
        commandList.add(FindNameCommand.COMMAND_WORD);
        commandList.add(FindPhoneCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(MonthlyExpenseCommand.COMMAND_WORD);
        commandList.add(MonthlyExpenseCommand.COMMAND_WORD + " MM/YYYY");
        commandList.add(ListTaskCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RedoExpenseCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD + " INDEX");
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(UndoExpenseCommand.COMMAND_WORD);
        commandList.add(UpdateTaskCommand.COMMAND_WORD);
        commandList.add(UpdateTaskCommand.COMMAND_WORD + " INDEX "
                + PREFIX_NAME + " "
                + PREFIX_BODY + " "
                + PREFIX_START + " "
                + PREFIX_END + " "
                + PREFIX_PRIORITY + " "
                + PREFIX_TAG);

        return commandList;
    }
    //@@


    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
