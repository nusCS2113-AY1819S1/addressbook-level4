package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfileWindowManagerTest {

    private ProfileWindowManager profileWindowManager;
    private Document doc;
    private String fileName;
    private String currentGender;
    private String currentUsername;
    private String currentHeight;
    private String currentWeight;
    private String currentDifficulty;
    private String currentDuration;
    private String currentCalories;

    @Before
    @SuppressWarnings("Duplicates")
    public void setUp() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        currentGender = divGender.ownText();
        currentUsername = divUsername.ownText();
        currentHeight = divHeight.ownText();
        currentWeight = divWeight.ownText();
        currentDifficulty = divDifficulty.ownText();
        currentCalories = divCalories.ownText();
        currentDuration = divDuration.ownText();
    }

    @Test
    public void isValidGenderTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validGender = "male";
        String invalidGender = "males";

        assertTrue(profileWindowManager.isValidGender(validGender));
        assertFalse(profileWindowManager.isValidGender(invalidGender));
    }

    @Test
    public void isValidWeightTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validWeight = "66.2";
        String invalidWeight = "94.88";
        String invalidWeight2 = "94.8kg";

        assertTrue(profileWindowManager.isValidWeight(validWeight));
        //Weight should be in the format xx.xx where x is any integer.
        assertFalse(profileWindowManager.isValidWeight(invalidWeight));
        assertFalse(profileWindowManager.isValidWeight(invalidWeight2));
    }

    @Test
    public void isValidHeightTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validHeight = "1.77";
        String invalidHeight = "1.734";
        String invalidHeight2 = "177";

        assertTrue(profileWindowManager.isValidHeight(validHeight));
        //Height should be in the format x.xx where x is any integer.
        assertFalse(profileWindowManager.isValidHeight(invalidHeight));
        assertFalse(profileWindowManager.isValidHeight(invalidHeight2));
    }

    @Test
    public void isValidUsernameTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validUsername = "John Doe";
        String invalidUsername = "John@Doe";

        assertTrue(profileWindowManager.isValidUsername(validUsername));
        //Name should only consists of alphanumeric characters
        assertFalse(profileWindowManager.isValidUsername(invalidUsername));
    }

    @Test
    public void convertStringToIntegerTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validStringInteger = "123";
        String validUpperStringInteger = "124";
        String validLowerStringInteger = "124";

        assertEquals(123, profileWindowManager.convertStringIntoInt(validStringInteger));
        assertNotEquals(123, profileWindowManager.convertStringIntoInt(validUpperStringInteger));
        assertNotEquals(123, profileWindowManager.convertStringIntoInt(validLowerStringInteger));
    }

    @Test
    public void isHigherCaloriesTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        int lowerCalories = 150;
        int higherCalories = 160;
        int sameCalories = 150;
        assertTrue(profileWindowManager.isHigherCalories(higherCalories, lowerCalories));

        //Should return false since lowerCalories is not higher than higherCalories
        assertFalse(profileWindowManager.isHigherCalories(lowerCalories, higherCalories));
        //Should return false since lowerCalories is not higher than sameCalories
        assertFalse(profileWindowManager.isHigherCalories(lowerCalories, sameCalories));
    }

    @Test
    public void isHigherDurationTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        int lowerDuration = 20;
        int higherDuration = 21;
        int sameDuration = 20;
        assertTrue(profileWindowManager.isHigherDuration(higherDuration, lowerDuration));

        //Should return false since lowerDuration is not higher than higherDuration
        assertFalse(profileWindowManager.isHigherDuration(lowerDuration, higherDuration));
        //Should return false since lowerDuration is not higher than sameDuration
        assertFalse(profileWindowManager.isHigherDuration(lowerDuration, sameDuration));
    }

    @Test
    public void isMoreDifficultTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String lowerDifficulty = "beginner";
        String higherDifficulty = "intermediate";
        String sameDifficulty = "beginner";
        String highestDifficulty = "advanced";
        assertTrue(profileWindowManager.isMoreDifficult(higherDifficulty, lowerDifficulty));

        //Should return false since lowerDifficulty is not more difficult than higherDifficulty
        assertFalse(profileWindowManager.isMoreDifficult(lowerDifficulty, higherDifficulty));
        //Should return false since lowerDifficulty is not more difficult than sameDifficulty
        assertFalse(profileWindowManager.isMoreDifficult(lowerDifficulty, sameDifficulty));
        //Should return true since higher Difficulty is more difficult than lowerDifficulty
        assertTrue(profileWindowManager.isMoreDifficult(higherDifficulty, lowerDifficulty));
        //Should return false since higherDifficulty is not more difficult than higherDifficulty
        assertFalse(profileWindowManager.isMoreDifficult(higherDifficulty, higherDifficulty));


    }

    @Test
    public void calculateBmiTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();

        String validHeight = "1.83";
        String validWeight = "88.1";
        String validWeight2 = "78.1";
        double actualBmi = profileWindowManager.calculateBmi(validHeight, validWeight);
        double actualBmi2 = profileWindowManager.calculateBmi(validHeight, validWeight2);
        double expectedBmi = 26.3;

        assertEquals(expectedBmi, actualBmi, 0.1);

        //Wrong expectedBMI should be not equal
        assertNotEquals(expectedBmi, actualBmi2);

    }

    @Test
    public void extractHeightTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divHeight = doc.getElementById("height");
        divHeight.text("Height: 1.69m");
        String profileHeight = divHeight.text();
        profileHeight = profileWindowManager.extractHeight(profileHeight);

        String expectedHeight = "1.69";
        assertEquals(expectedHeight, profileHeight);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void extractWeightTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divWeight = doc.getElementById("weight");
        divWeight.text("Weight: 67.9kg");
        String profileWeight = divWeight.text();
        profileWeight = profileWindowManager.extractWeight(profileWeight);

        String expectedWeight = "67.9";
        assertEquals(expectedWeight, profileWeight);
    }

    @Test
    public void trimmedCaloriesTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divCalories = doc.getElementById("calories");
        divCalories.text("Calories: 170");
        String profileCalories = divCalories.text();
        profileCalories = profileWindowManager.trimmedCalories(profileCalories);

        String expectedCalories = "170";
        assertEquals(expectedCalories, profileCalories);
    }

    @Test
    public void trimmedUsernameTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divUsername = doc.getElementById("username");
        divUsername.text("Username: Apple");
        String profileUsername = divUsername.text();
        profileUsername = profileWindowManager.trimmedUsername(profileUsername);

        String expectedUsername = "Apple";
        assertEquals(expectedUsername, profileUsername);
    }

    @Test
    public void trimmedGenderTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        divGender.text("Gender: male");
        String profileGender = divGender.text();
        profileGender = profileWindowManager.trimmedGender(profileGender);

        String expectedGender = "male";
        assertEquals(expectedGender, profileGender);
    }

    @Test
    public void trimmedFullDurationTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divDuration = doc.getElementById("duration");
        divDuration.text("Duration: 17m");
        String profileDuration = divDuration.text();
        profileDuration = profileWindowManager.trimmedFullDuration(profileDuration);

        String expectedDuration = "17";
        assertEquals(expectedDuration, profileDuration);
    }

    @Test
    public void trimmedDurationTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divDuration = doc.getElementById("duration");
        divDuration.text("Duration: 17m");
        String profileDuration = divDuration.text();
        profileDuration = profileWindowManager.trimmedDuration(profileDuration);

        String expectedDuration = "17m";
        assertEquals(expectedDuration, profileDuration);
    }

    @Test
    public void trimmedDifficultyTest() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divDifficulty = doc.getElementById("difficulty");
        divDifficulty.text("Difficulty: intermediate");
        String profileDifficulty = divDifficulty.text();
        profileDifficulty = profileWindowManager.trimmedDifficulty(profileDifficulty);

        String expectedDifficulty = "intermediate";
        assertEquals(expectedDifficulty, profileDifficulty);
    }

    @After
    @SuppressWarnings("Duplicates")
    public void revert() throws IOException {
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        divGender.text(currentGender);
        divHeight.text(currentHeight);
        divUsername.text(currentUsername);
        divDifficulty.text(currentDifficulty);
        divWeight.text(currentWeight);
        divCalories.text(currentCalories);
        divDuration.text(currentDuration);
    }
}
