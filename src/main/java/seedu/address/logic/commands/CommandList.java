package seedu.address.logic.commands;
import java.util.ArrayList;
import java.util.Collections;

import seedu.address.request.requestcommands.DeleteRequestCommand;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestcommands.ToggleRequestCommand;




/**
 * Gives out the command list for different users
 */
public class CommandList {
    private ArrayList<String> commandList = new ArrayList<>();

    public CommandList() {
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(CheckCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteRequestCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);

        commandList.add(RequestCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(SellCommand.COMMAND_WORD);
        commandList.add(ViewStatisticCommand.COMMAND_WORD);
        commandList.add(StockCommand.COMMAND_WORD);
        commandList.add(ToggleRequestCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        Collections.sort(commandList);
    }
    public ArrayList<String> getAccountantCommands() {
        commandList.remove(AddCommand.COMMAND_WORD);
        commandList.remove(EditCommand.COMMAND_WORD);
        commandList.remove(SellCommand.COMMAND_WORD);
        commandList.remove(DeleteCommand.COMMAND_WORD);
        commandList.remove(StockCommand.COMMAND_WORD);
        commandList.remove(ClearCommand.COMMAND_WORD);
        commandList.remove(CheckCommand.COMMAND_WORD);
        commandList.remove(RequestCommand.COMMAND_WORD);
        commandList.remove(DeleteRequestCommand.COMMAND_WORD);

        return commandList;
    }

    public ArrayList<String> getStudentCommands() {
        commandList.remove(AddCommand.COMMAND_WORD);
        commandList.remove(EditCommand.COMMAND_WORD);
        commandList.remove(SellCommand.COMMAND_WORD);
        commandList.remove(DeleteCommand.COMMAND_WORD);
        commandList.remove(StockCommand.COMMAND_WORD);
        commandList.remove(ClearCommand.COMMAND_WORD);
        commandList.remove(CheckCommand.COMMAND_WORD);
        commandList.remove(ToggleRequestCommand.COMMAND_WORD);
        commandList.remove(ViewStatisticCommand.COMMAND_WORD);
        commandList.remove(DeleteRequestCommand.COMMAND_WORD);

        return commandList;
    }

    public ArrayList<String> getCommandList() {
        return commandList;
    }

}
