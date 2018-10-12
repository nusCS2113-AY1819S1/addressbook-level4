package seedu.address.logic.parser;


import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.Model;

import java.util.*;


import static seedu.address.logic.commands.MergeCommand.MESSAGE_NOT_MERGED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MERGE;


public class MergeCommandParser implements Parser<MergeCommand> {

    public MergeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MERGE);

        List<String> indices = argMultimap.getAllValues(PREFIX_MERGE);


        if(indices.size()<2){
            throw new ParseException(MESSAGE_NOT_MERGED);
        }

        return new MergeCommand(indices);
    }
}
