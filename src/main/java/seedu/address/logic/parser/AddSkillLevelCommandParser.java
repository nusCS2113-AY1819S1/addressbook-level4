package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddSkillLevelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Skill;
import seedu.address.model.person.SkillLevel;

/**
 * Parses input arguments and creates a new {@code AddSkillLevelCommand} object
 */
public class AddSkillLevelCommandParser implements Parser<AddSkillLevelCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSkillLevelCommand}
     * and returns a {@code AddSkillLevelCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSkillLevelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SKILLLEVEL, PREFIX_SKILL);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSkillLevelCommand.MESSAGE_USAGE), ive);
        }
        Skill skill = new Skill(argMultimap.getValue(PREFIX_SKILL).orElse(""));
        SkillLevel level = new SkillLevel(Integer.parseInt(argMultimap.getValue(PREFIX_SKILLLEVEL).orElse("")));

        return new AddSkillLevelCommand(index, skill, level);
    }
}
