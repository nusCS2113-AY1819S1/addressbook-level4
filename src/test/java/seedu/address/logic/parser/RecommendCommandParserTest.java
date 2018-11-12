package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONAL_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONAL_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONAL_DURATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.RecommendCommand;
import seedu.address.model.ProfileWindowManager;
import seedu.address.model.RecommendArguments;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Mode;

public class RecommendCommandParserTest {
    private RecommendCommandParser parser = new RecommendCommandParser();

    @Test
    public void parse_optionalFieldsMissing_success() {
        // Valid Single Recommend Calories without Difficulty and Duration fields
        Optional<Calories> calories = Optional.of(new Calories("1"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES + "1",
                new RecommendCommand(expectedRecommendArguments));

        // Valid Recommend Multiple Difficulty without Calories and Duration fields
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("beginner"));
        mode = Optional.of(new Mode("multiple 1"));
        expectedRecommendArguments = new RecommendArguments.Builder().withDifficulty(difficulty,
                Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "multiple 1" + " " + PREFIX_DIFFICULTY + "beginner",
                new RecommendCommand(expectedRecommendArguments));

        // Valid Recommend All Duration without Calories and Difficulty fields
        Optional<Duration> duration = Optional.of(new Duration("1m"));
        mode = Optional.of(new Mode("all"));
        expectedRecommendArguments = new RecommendArguments.Builder().withDuration(duration,
                Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "all" + " " + PREFIX_DURATION + "1m",
                new RecommendCommand(expectedRecommendArguments));

        // Valid Calories and Difficulty without Duration field
        calories = Optional.of(new Calories("1000"));
        difficulty = Optional.of(new Difficulty("intermediate"));
        mode = Optional.of(new Mode("single"));
        expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(false))
                .withDifficulty(difficulty, Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES + "1000"
                        + " " + PREFIX_DIFFICULTY + "intermediate",
                new RecommendCommand(expectedRecommendArguments));

        // Valid Calories and Duration without Difficulty field
        calories = Optional.of(new Calories("500"));
        duration = Optional.of(new Duration("1000m"));
        mode = Optional.of(new Mode("multiple 1000"));
        expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(false))
                .withDuration(duration, Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "multiple 1000" + " " + PREFIX_CALORIES
                        + "500" + " " + PREFIX_DURATION + "1000m",
                new RecommendCommand(expectedRecommendArguments));

        // Valid Difficulty and Duration without Calories Field
        difficulty = Optional.of(new Difficulty("advanced"));
        duration = Optional.of(new Duration("500m"));
        mode = Optional.of(new Mode("all"));
        expectedRecommendArguments = new RecommendArguments.Builder().withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false)).withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "all" + " " + PREFIX_DIFFICULTY + "advanced"
                        + " " + PREFIX_DURATION + "500m",
                new RecommendCommand(expectedRecommendArguments));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // Valid Calories,Difficulty and Duration fields
        Optional<Calories> calories = Optional.of(new Calories("750"));
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("intermediate"));
        Optional<Duration> duration = Optional.of(new Duration("750m"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false))
                .withDifficulty(difficulty, Optional.of(false)).withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES + "750"
                        + " " + PREFIX_DIFFICULTY + "intermediate"
                        + " " + PREFIX_DURATION + "750m",
                new RecommendCommand(expectedRecommendArguments));
    }

    @Test
    public void parse_optionalFieldsPresent_success() {
        // 1 Optionals 2 Non-optionals
        Optional<Calories> calories = Optional.of(new Calories("875"));
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("advanced"));
        Optional<Duration> duration = Optional.of(new Duration("875m"));
        Optional<Mode> mode = Optional.of(new Mode("multiple 500"));
        RecommendArguments expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(true))
                .withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "multiple 500" + " " + PREFIX_OPTIONAL_CALORIES
                        + "875" + " " + PREFIX_DIFFICULTY
                        + "advanced" + " " + PREFIX_DURATION + "875m",
                new RecommendCommand(expectedRecommendArguments));

        // 2 Optionals 1 Non-optionals
        calories = Optional.of(new Calories("375"));
        difficulty = Optional.of(new Difficulty("beginner"));
        duration = Optional.of(new Duration("375m"));
        mode = Optional.of(new Mode("all"));
        expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(true))
                .withDifficulty(difficulty, Optional.of(true))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "all" + " " + PREFIX_OPTIONAL_CALORIES + "375"
                        + " " + PREFIX_OPTIONAL_DIFFICULTY
                        + "beginner" + " " + PREFIX_DURATION + "375m",
                new RecommendCommand(expectedRecommendArguments));

        // 3 Optionals 0 Non-optionals
        calories = Optional.of(new Calories("625"));
        difficulty = Optional.of(new Difficulty("intermediate"));
        duration = Optional.of(new Duration("625m"));
        mode = Optional.of(new Mode("single"));
        expectedRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(true))
                .withDifficulty(difficulty, Optional.of(true))
                .withDuration(duration, Optional.of(true))
                .withMode(mode).build();
        assertParseSuccess(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_OPTIONAL_CALORIES + "625"
                        + " " + PREFIX_OPTIONAL_DIFFICULTY
                        + "intermediate" + " " + PREFIX_OPTIONAL_DURATION + "625m",
                new RecommendCommand(expectedRecommendArguments));
    }

    @Test
    public void parse_noFieldsPresent_success() throws IOException {
        // No fields
        ProfileWindowManager profileWindowManager = ProfileWindowManager.getInstance();
        RecommendArguments.Builder expectedRecommendArgumentsBuilder = new RecommendArguments.Builder();
        if (!profileWindowManager.isCaloriesAny()) {
            expectedRecommendArgumentsBuilder.withCalories(profileWindowManager.extractCalories(), Optional.of(false));
        }
        if (!profileWindowManager.isDifficultyAny()) {
            expectedRecommendArgumentsBuilder.withDifficulty(profileWindowManager.extractDifficulty(),
                    Optional.of(false));
        }
        if (!profileWindowManager.isDurationAny()) {
            expectedRecommendArgumentsBuilder.withDuration(profileWindowManager.extractDuration(), Optional.of(false));
        }
        RecommendArguments expectedRecommendArguments = expectedRecommendArgumentsBuilder.build();
        assertParseSuccess(parser, " ", new RecommendCommand(expectedRecommendArguments));
    }

    @Test
    public void parse_invalidOptionalFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_OPTIONALS);

        // 2 Optionals 0 Non-optionals
        assertParseFailure(parser, " " + PREFIX_MODE + "multiple 750" + " " + PREFIX_OPTIONAL_CALORIES
                + "500" + " " + PREFIX_OPTIONAL_DIFFICULTY
                + "beginner", expectedMessage);

        // 1 Optionals 0 Non-optionals
        assertParseFailure(parser, " " + PREFIX_MODE + "all" + " " + PREFIX_OPTIONAL_CALORIES
                + "200", expectedMessage);
    }

    @Test
    public void parse_invalidFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE);

        // Gibberish input with alphabet, integer, special character
        assertParseFailure(parser, " " + "a1!", expectedMessage);

        // Missing Mode Prefix but contains other prefixes
        assertParseFailure(parser, " " + PREFIX_DIFFICULTY + "advanced", expectedMessage);

        // Contains Mode Prefix but missing other prefixes
        assertParseFailure(parser, " " + PREFIX_MODE + "multiple 250", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Modes for strings that aren't single, all or multiple INTEGER
        assertParseFailure(parser, " " + PREFIX_MODE + "double" + " " + PREFIX_CALORIES
                + "100", Mode.MESSAGE_MODE_CONSTRAINTS);

        // Invalid Multiple Mode < 1
        assertParseFailure(parser, " " + PREFIX_MODE + "multiple 0" + " " + PREFIX_DIFFICULTY
                + "beginner", Mode.MESSAGE_MODE_CONSTRAINTS);

        // Invalid Multiple Mode > 1000
        assertParseFailure(parser, " " + PREFIX_MODE + "multiple 1001" + " " + PREFIX_DURATION
                + "100m", Mode.MESSAGE_MODE_CONSTRAINTS);

        // Invalid Calories < 1
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES
                + "0", Calories.MESSAGE_CALORIES_CONSTRAINTS);

        // Invalid Calories > 1000
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES
                + "1001", Calories.MESSAGE_CALORIES_CONSTRAINTS);

        // Invalid Calories for non-integer
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_CALORIES
                + "hundred", Calories.MESSAGE_CALORIES_CONSTRAINTS);

        // Invalid Difficulty for non-case sensitive inputs
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_DIFFICULTY + "beginNer",
                Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);

        // Invalid Difficulty for integer
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_DIFFICULTY
                + "1", Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);

        // Invalid Duration for < 1m
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_DURATION
                + "0m", Duration.MESSAGE_DURATION_CONSTRAINTS);

        // Invalid Duration for > 1000m
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_DURATION
                + "1001m", Duration.MESSAGE_DURATION_CONSTRAINTS);

        // Invalid Duration for non-integer
        assertParseFailure(parser, " " + PREFIX_MODE + "single" + " " + PREFIX_DURATION
                + "fivem", Duration.MESSAGE_DURATION_CONSTRAINTS);
    }

}
