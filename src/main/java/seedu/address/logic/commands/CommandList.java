package seedu.address.logic.commands;

import java.util.ArrayList;
/**
 * Gives out the command list for different users
 */
public class CommandList {
    private ArrayList<String> commandList = new ArrayList<>();

    public CommandList() {
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(SellCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(StockCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(CheckCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(ViewStatisticCommand.COMMAND_WORD);
    }
    public ArrayList<String> getAccountantCommands() {
        commandList.remove(AddCommand.COMMAND_WORD);
        commandList.remove(EditCommand.COMMAND_WORD);
        commandList.remove(SellCommand.COMMAND_WORD);
        commandList.remove(DeleteCommand.COMMAND_WORD);
        commandList.remove(StockCommand.COMMAND_WORD);
        commandList.remove(ClearCommand.COMMAND_WORD);
        commandList.remove(CheckCommand.COMMAND_WORD);
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
        commandList.remove(ViewStatisticCommand.COMMAND_WORD);
        return commandList;
    }

    public ArrayList<String> getCommandList() {
        return commandList;
    }

}
