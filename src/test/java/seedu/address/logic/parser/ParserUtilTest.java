package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "James's workout&";
    private static final String INVALID_TYPE = "strength + cardio";
    private static final String INVALID_DURATION = "5 minutes";
    private static final String INVALID_DIFFICULTY = "difficult";
    private static final String INVALID_EQUIPMENT = "dumbbell + mat";
    private static final String INVALID_MUSCLE = "bicep + tricep";
    private static final String INVALID_CALORIES = "123 calories";
    //no invalid instructions
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "commando workout";
    private static final String VALID_TYPE = "strength";
    private static final String VALID_DURATION = "60m";
    private static final String VALID_DIFFICULTY = "intermediate";
    private static final String VALID_EQUIPMENT = "bench";
    private static final String VALID_MUSCLE = "biceps";
    private static final String VALID_CALORIES = "150";
    private static final String VALID_INSTRUCTION = "set 1: bicep curl reps: 4-6 set 2: tricep extension reps: 4-6";
    private static final String VALID_TAG_1 = "crazy";
    private static final String VALID_TAG_2 = "fun";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_WORKOUT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_WORKOUT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void paxrseName_null_throwsNullPointerEception() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseType(null));
    }

    @Test
    public void parseType_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseType(INVALID_TYPE));
    }

    @Test
    public void parseType_validValueWithoutWhitespace_returnsType() throws Exception {
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(VALID_TYPE));
    }

    @Test
    public void parseType_validValueWithWhitespace_returnsTrimmedType() throws Exception {
        String typeWithWhitespace = WHITESPACE + VALID_TYPE + WHITESPACE;
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(typeWithWhitespace));
    }

    @Test
    public void parseDuration_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDuration(null));
    }

    @Test
    public void parseDuration_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION));
    }

    @Test
    public void parseDuration_validValueWithoutWhitespace_returnsDuration() throws Exception {
        Duration expectedDuration = new Duration(VALID_DURATION);
        assertEquals(expectedDuration, ParserUtil.parseDuration(VALID_DURATION));
    }

    @Test
    public void parseDuration_validValueWithWhitespace_returnsTrimmedDuration() throws Exception {
        String durationWithWhitespace = WHITESPACE + VALID_DURATION + WHITESPACE;
        Duration expectedDuration = new Duration(VALID_DURATION);
        assertEquals(expectedDuration, ParserUtil.parseDuration(durationWithWhitespace));
    }

    @Test
    public void parseDifficulty_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDifficulty(null));
    }

    @Test
    public void parseDifficulty_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDifficulty(INVALID_DIFFICULTY));
    }

    @Test
    public void parseDifficulty_validValueWithoutWhitespace_returnsDifficulty() throws Exception {
        Difficulty expectedDifficulty = new Difficulty(VALID_DIFFICULTY);
        assertEquals(expectedDifficulty, ParserUtil.parseDifficulty(VALID_DIFFICULTY));
    }

    @Test
    public void parseDifficulty_validValueWithWhitespace_returnsTrimmedDifficulty() throws Exception {
        String difficultyWithWhitespace = WHITESPACE + VALID_DIFFICULTY + WHITESPACE;
        Difficulty expectedDifficulty = new Difficulty(VALID_DIFFICULTY);
        assertEquals(expectedDifficulty, ParserUtil.parseDifficulty(difficultyWithWhitespace));
    }

    @Test
    public void parseEquipment_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEquipment(null));
    }

    @Test
    public void parseEquipment_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEquipment(INVALID_EQUIPMENT));
    }

    @Test
    public void parseEquipment_validValueWithoutWhitespace_returnsEquipment() throws Exception {
        Equipment expectedEquipment = new Equipment(VALID_EQUIPMENT);
        assertEquals(expectedEquipment, ParserUtil.parseEquipment(VALID_EQUIPMENT));
    }

    @Test
    public void parseEquipment_validValueWithWhitespace_returnsTrimmedEquipment() throws Exception {
        String equipmentWithWhitespace = WHITESPACE + VALID_EQUIPMENT + WHITESPACE;
        Equipment expectedEquipment = new Equipment(VALID_EQUIPMENT);
        assertEquals(expectedEquipment, ParserUtil.parseEquipment(equipmentWithWhitespace));
    }

    @Test
    public void parseMuscle_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMuscle(null));
    }

    @Test
    public void parseMuscle_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMuscle(INVALID_MUSCLE));
    }

    @Test
    public void parseMuscle_validValueWithoutWhitespace_returnsMuscle() throws Exception {
        Muscle expectedMuscle = new Muscle(VALID_MUSCLE);
        assertEquals(expectedMuscle, ParserUtil.parseMuscle(VALID_MUSCLE));
    }

    @Test
    public void parseMuscle_validValueWithWhitespace_returnsTrimmedMuscle() throws Exception {
        String muscleWithWhitespace = WHITESPACE + VALID_MUSCLE + WHITESPACE;
        Muscle expectedMuscle = new Muscle(VALID_MUSCLE);
        assertEquals(expectedMuscle, ParserUtil.parseMuscle(muscleWithWhitespace));
    }

    @Test
    public void parseCalories_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCalories(null));
    }

    @Test
    public void parseCalories_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCalories(INVALID_CALORIES));
    }

    @Test
    public void parseCalories_validValueWithoutWhitespace_returnsCalories() throws Exception {
        Calories expectedCalories = new Calories(VALID_CALORIES);
        assertEquals(expectedCalories, ParserUtil.parseCalories(VALID_CALORIES));
    }

    @Test
    public void parseCalories_validValueWithWhitespace_returnsTrimmedCalories() throws Exception {
        String caloriesWithWhitespace = WHITESPACE + VALID_CALORIES + WHITESPACE;
        Calories expectedCalories = new Calories(VALID_CALORIES);
        assertEquals(expectedCalories, ParserUtil.parseCalories(caloriesWithWhitespace));
    }

    @Test
    public void parseInstruction_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseInstruction(null));
    }

    @Test
    public void parseInstruction_validValueWithoutWhitespace_returnsInstruction() throws Exception {
        Instruction expectedInstruction = new Instruction(VALID_INSTRUCTION);
        assertEquals(expectedInstruction, ParserUtil.parseInstruction(VALID_INSTRUCTION));
    }

    @Test
    public void parseInstruction_validValueWithWhitespace_returnsTrimmedInstruction() throws Exception {
        String instructionWithWhitespace = WHITESPACE + VALID_INSTRUCTION + WHITESPACE;
        Instruction expectedInstruction = new Instruction(VALID_INSTRUCTION);
        assertEquals(expectedInstruction, ParserUtil.parseInstruction(instructionWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
