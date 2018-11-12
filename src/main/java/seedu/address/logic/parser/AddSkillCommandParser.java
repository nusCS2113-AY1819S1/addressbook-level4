package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Skill;

/**
 * Parses input arguments and creates a new {@code AddSkillCommand} object
 */
public class AddSkillCommandParser implements Parser<AddSkillCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSkillCommand}
     * and returns a {@code AddSkillCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSkillCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SKILL);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE), ive);
        }
        String skill = argMultimap.getValue(PREFIX_SKILL).orElse("");
        return new AddSkillCommand(index, new Skill(skill));
    }
}
