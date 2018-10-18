package seedu.address.logic.commands;

import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.*;
import seedu.address.model.*;

public class LoginIvleCommand extends Command {

    public static final String COMMAND_WORD="download";
    public static final String MESSAGE_USAGE="download pass/(password) user/(username) mod/(moduleCode)";

    private String username;
    private String password;
    private String moduleCode;


    public LoginIvleCommand (String username,String password,String moduleCode){
        this.password=password;
        this.username=username;
        this.moduleCode=moduleCode;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history)
    {
        String dummy="bingbongbong";
        return new CommandResult(dummy);
    }

}
