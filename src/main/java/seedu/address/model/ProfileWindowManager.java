package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;

/**
 * Acts as a Facade for any methods to do with ProfileWindow
 * Singleton Pattern
 */
public class ProfileWindowManager {

    public static final String CALORIES_VALIDATION_REGEX =
            "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|1000)|(any)";
    public static final String DURATION_VALIDATION_REGEX =
            "(([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|1000)[m])|(any)";
    private static final String GENDER_VALIDATION_REGEX = "(male)|(female)";
    private static final String HEIGHT_VALIDATION_REGEX = "\\d{1}\\.\\d{2}";
    private static final String WEIGHT_VALIDATION_REGEX = "\\d{2,3}\\.\\d{1}";
    private static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}|'][\\p{Alnum} |' ]*";
    private static final String DIFFICULTY_VALIDATION_REGEX = "(beginner)|(intermediate)|(advanced)|(any)";

    private static ProfileWindowManager singleInstance = null;

    private Element gender;
    private Element username;
    private Element height;
    private Element weight;
    private Element calories;
    private Element difficulty;
    private Element duration;
    private Element bmi;

    private Document doc;
    private String fileName;
    private FileUtils fileUtils = new FileUtils();

    private ProfileWindowManager() throws IOException {
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        this.gender = doc.getElementById("gender");
        this.username = doc.getElementById("username");
        this.height = doc.getElementById("height");
        this.weight = doc.getElementById("weight");
        this.calories = doc.getElementById("calories");
        this.difficulty = doc.getElementById("difficulty");
        this.duration = doc.getElementById("duration");
        this.bmi = doc.getElementById("bmi");
    }

    public static ProfileWindowManager getInstance() throws IOException {
        if (singleInstance == null) {
            synchronized (ProfileWindowManager.class) {
                singleInstance = new ProfileWindowManager();
            }
        }
        return singleInstance;
    }

    /**
     * Writes to File.
     * @throws IOException if the file is not found
     */
    public void writeToFile() throws IOException {
        File temp = File.createTempFile("tempfile", ".html");
        FileUtils.writeStringToFile(temp, doc.outerHtml(), "UTF-8");
        File newFile = new File(fileName);
        fileUtils.copyFile(temp, newFile);
        fileUtils.forceDelete(temp);
    }

    public Element getGender() {
        return gender;
    }

    public Element getUsername() {
        return username;
    }

    public Element getHeight() {
        return height;
    }

    public Element getWeight() {
        return weight;
    }

    public Element getCalories() {
        return calories;
    }

    public Element getDifficulty() {
        return difficulty;
    }

    public Element getDuration() {
        return duration;
    }

    public void setGender(String gender) {
        this.gender.text(gender);
    }

    public void setUsername(String username) {
        this.username.text(username);
    }

    public void setHeight(String height) {
        this.height.text(height);
    }

    public void setWeight(String weight) {
        this.weight.text(weight);
    }

    public void setCalories(String calories) {
        this.calories.text(calories);
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.text(difficulty);
    }

    public void setDuration(String duration) {
        this.duration.text(duration);
    }

    public void setBmi(String bmi) {
        this.bmi.text(bmi);
    }

    /**
     * Trims the string height
     */
    public String extractHeight(String height) {
        height = height.replaceFirst("Height: ", "");
        height = height.replaceFirst("m", "");
        return height;
    }

    /**
     * Trims the string weight
     */
    public String extractWeight(String weight) {
        weight = weight.replaceFirst("kg", "");
        weight = weight.replaceFirst("Weight: ", "");
        return weight;
    }

    /**
     * Trims the string calories
     */
    public String trimmedCalories(String calories) {
        calories = calories.replaceFirst("Calories: ", "");
        return calories;
    }

    /**
     * Trims the string duration with the "m" remaining
     */
    public String trimmedDuration(String duration) {
        duration = duration.replaceFirst("Duration: ", "");
        return duration;
    }

    /**
     * Trims the string duration without the "m" remaining
     */
    public String trimmedFullDuration(String duration) {
        duration = duration.replaceFirst("Duration: ", "");
        duration = duration.replaceFirst("m", "");
        return duration;
    }

    /**
     * Trims the string difficulty
     */
    public String trimmedDifficulty(String difficulty) {
        difficulty = difficulty.replaceFirst("Difficulty: ", "");
        return difficulty;
    }

    /**
     * Trims the string gender
     */
    public String trimmedGender(String gender) {
        gender = gender.replaceFirst("Gender: ", "");
        return gender;
    }

    /**
     * Trims the string username
     */
    public String trimmedUsername(String username) {
        username = username.replaceFirst("Username: ", "");
        return username;
    }

    public boolean isCaloriesAny() {
        return trimmedCalories(calories.ownText()).equals("any");
    }

    public boolean isDifficultyAny() {
        return trimmedDifficulty(difficulty.ownText()).equals("any");
    }

    public boolean isDurationAny() {
        return trimmedDuration(duration.ownText()).equals("any");
    }


    public Optional<Calories> extractCalories() {
        return Optional.of(new Calories(trimmedCalories(calories.ownText())));
    }

    public Optional<Difficulty> extractDifficulty() {
        return Optional.of(new Difficulty(trimmedDifficulty(difficulty.ownText())));
    }

    public Optional<Duration> extractDuration() {
        return Optional.of(new Duration(trimmedDuration(duration.ownText())));
    }

    /**
     * @param height to be read from profile window
     * @param weight to be read from profile window
     * @return bmi value
     */
    public double calculateBmi(String height, String weight) {
        double h = Double.parseDouble(extractHeight(height));
        double w = Double.parseDouble(extractWeight(weight));
        return w / (h * h);
    }
    /**
     * Converts a String to integer.
     * @param string to be converted
     * @return integer
     */
    public int convertStringIntoInt(String string) {
        return Integer.parseInt(string);
    }

    /**
     * Returns true if the given string is a valid calories
     */
    public boolean isValidCalories(String calories) {
        return calories.toLowerCase().matches(CALORIES_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid difficulty
     */
    public boolean isValidDifficulty(String difficulty) {
        return difficulty.toLowerCase().matches(DIFFICULTY_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid difficulty
     */
    public boolean isValidDuration(String duration) {
        return duration.toLowerCase().matches(DURATION_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid gender
     */
    public boolean isValidGender(String gender) {
        return gender.toLowerCase().matches(GENDER_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid height
     */
    public boolean isValidHeight(String height) {
        return height.matches(HEIGHT_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid weight
     */
    public boolean isValidWeight(String weight) {
        return weight.matches(WEIGHT_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid username
     */
    public boolean isValidUsername(String username) {
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if the workout's difficulty is more difficult than the user's difficulty
     */
    public boolean isMoreDifficult(String workout, String user) {
        if (user.matches("any")) {
            return false;
        }
        if (workout.matches("beginner")) {
            return false;
        }
        if ((workout.matches("intermediate")) && (user.matches("beginner"))) {
            return true;
        }
        if ((workout.matches("advanced")) && ((user.matches("beginner"))
                || (user.matches("intermediate")))) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the workout's calories is higher than the user's calories
     */
    public boolean isHigherCalories(int workout, int user) {
        return (workout > user);
    }

    /**
     * Returns true if the workout's duration is higher than the user's duration
     */
    public boolean isHigherDuration(int workout, int user) {
        return (workout > user);
    }
}
