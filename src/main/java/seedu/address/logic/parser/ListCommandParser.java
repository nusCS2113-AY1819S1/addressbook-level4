package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_TAG);

        if (argMultimap.getValue(CliSyntax.PREFIX_TAG).isPresent()) {
            return new ListCommand(ListCommand.TYPE_TAG, ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG)));
        } else {
            return new ListCommand(ListCommand.TYPE_ALL, new ArrayList<>());
        }
    }
}
