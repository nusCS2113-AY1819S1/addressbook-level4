package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CALORIES;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_HEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_WEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_CALORIES;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_HEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_WEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.stream.Stream;

import org.jsoup.nodes.Element;

import seedu.address.logic.commands.ModifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ProfileWindowManager;

/**
 * Parses input arguments and creates a new ModifyCommand object
 */
public class ModifyCommandParser implements Parser<ModifyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ModifyCommand}
     * and returns a {@code ModifyCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ModifyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_HEIGHT, PREFIX_WEIGHT,
                PREFIX_DIFFICULTY, PREFIX_GENDER, PREFIX_CALORIES, PREFIX_DURATION);

        if (!isPrefixPresent(argMultimap, PREFIX_USERNAME, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_DIFFICULTY,
                PREFIX_GENDER, PREFIX_CALORIES, PREFIX_DURATION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyCommand.MESSAGE_USAGE));
        }
        ProfileWindowManager profileWindowManager;
        try {
            profileWindowManager = ProfileWindowManager.getInstance();
            Element divGender = profileWindowManager.getGender();
            Element divUsername = profileWindowManager.getUsername();
            Element divHeight = profileWindowManager.getHeight();
            Element divWeight = profileWindowManager.getWeight();
            Element divCalories = profileWindowManager.getCalories();
            Element divDuration = profileWindowManager.getDuration();
            Element divDifficulty = profileWindowManager.getDifficulty();
            String newGender = divGender.ownText();
            String newHeight = divHeight.ownText();
            String newWeight = divWeight.ownText();
            String newDifficulty = divDifficulty.ownText();
            String newUsername = divUsername.ownText();
            String newCalories = divCalories.ownText();
            String newDuration = divDuration.ownText();

            if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
                String gender = argMultimap.getValue(PREFIX_GENDER).get();
                if (!profileWindowManager.isValidGender(gender)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_GENDER, MESSAGE_VALID_GENDER));
                }
                profileWindowManager.setGender("Gender: " + gender);
                newGender = gender;
            }
            if (argMultimap.getValue(PREFIX_USERNAME).isPresent()) {
                String username = argMultimap.getValue(PREFIX_USERNAME).get();
                if (!profileWindowManager.isValidUsername(username)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_USERNAME, MESSAGE_VALID_USERNAME));
                }
                profileWindowManager.setUsername(username);
                newUsername = username;
            }
            if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
                String height = argMultimap.getValue(PREFIX_HEIGHT).get();
                if (!profileWindowManager.isValidHeight(height)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_HEIGHT, MESSAGE_VALID_HEIGHT));
                }
                profileWindowManager.setHeight("Height: " + height + "m");
                newHeight = height;
            }
            if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
                String weight = argMultimap.getValue(PREFIX_WEIGHT).get();
                if (!profileWindowManager.isValidWeight(weight)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_WEIGHT, MESSAGE_VALID_WEIGHT));
                }
                profileWindowManager.setWeight("Weight: " + weight + "kg");
                newWeight = weight;
            }
            if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
                String difficulty = argMultimap.getValue(PREFIX_DIFFICULTY).get();
                if (!profileWindowManager.isValidDifficulty(difficulty)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_DIFFICULTY, MESSAGE_VALID_DIFFICULTY));
                }
                profileWindowManager.setDifficulty("Difficulty: " + difficulty);
                newDifficulty = difficulty;
            }
            if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
                String calories = argMultimap.getValue(PREFIX_CALORIES).get();
                if (!profileWindowManager.isValidCalories(calories)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_CALORIES, MESSAGE_VALID_CALORIES));
                }
                profileWindowManager.setCalories("Calories: " + calories);
                newCalories = calories;
            }
            if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
                String duration = argMultimap.getValue(PREFIX_DURATION).get();
                if (!profileWindowManager.isValidDuration(duration)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_DURATION, MESSAGE_VALID_DURATION));
                }
                profileWindowManager.setDuration("Duration: " + duration);
                newDuration = duration;
            }

            DecimalFormat df = new DecimalFormat("#.#");
            profileWindowManager.setBmi("BMI : " + df.format(profileWindowManager.calculateBmi(newHeight, newWeight)));
            profileWindowManager.writeToFile();

            return new ModifyCommand(newGender, newUsername, newHeight, newWeight, newCalories, newDifficulty,
                    newDuration);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            // Dummy
            return new ModifyCommand(" ", "", "", "", "", "",
                    "");
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
