package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyAllPayCommand.ModSalaryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Bonus;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ModifyAllPayCommandParser implements Parser<ModifyAllPayCommand> {
    private static final double BONUS_UPPER_LIMIT = 24.0;
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModifyAllPayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim().toLowerCase();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SALARY, PREFIX_BONUS);

        if (trimmedArgs.isEmpty() || (!argMultimap.getValue(PREFIX_BONUS).isPresent()
                && !argMultimap.getValue(PREFIX_SALARY).isPresent()) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyAllPayCommand.MESSAGE_USAGE));
        }

        if (!didPrefixAppearOnlyOnce(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyAllPayCommand.MESSAGE_USAGE));
        }

        ModSalaryDescriptor modSalaryDescriptor = new ModSalaryDescriptor();

        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            modSalaryDescriptor.setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }

        if (argMultimap.getValue(PREFIX_BONUS).isPresent()) {
            Bonus bonusInput = ParserUtil.parseBonus(argMultimap.getValue(PREFIX_BONUS).get());

            double bonus = Double.parseDouble(argMultimap.getValue(PREFIX_BONUS).get());

            if (bonus > BONUS_UPPER_LIMIT) {
                throw new ParseException(Bonus.MESSAGE_BONUS_CONSTRAINTS);
            }

            modSalaryDescriptor.setBonus(bonusInput);
        }

        if (!modSalaryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ModifyAllPayCommand.MESSAGE_NOT_MODIFIED);
        }

        return new ModifyAllPayCommand(modSalaryDescriptor);
    }

    /**
     * Check whether prefixes appeared more than once within the argument.
     * @param argument The user's input
     */
    private boolean didPrefixAppearOnlyOnce(String argument) {
        String prefixSalary = " " + PREFIX_SALARY.toString();
        String prefixBonus = " " + PREFIX_BONUS.toString();

        return argument.indexOf(prefixSalary) == argument.lastIndexOf(prefixSalary)
                && argument.indexOf(prefixBonus) == argument.lastIndexOf(prefixBonus);
    }
}
