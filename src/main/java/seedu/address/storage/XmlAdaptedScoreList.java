package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.grade.ScoreList;
import seedu.address.model.grade.Test;



/**
 * JAXB-friendly version of the test scores.
 */
public class XmlAdaptedScoreList {


    @XmlElement
    private List<XmlAdaptedTest> scoreList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedscoreList.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedScoreList() {
    }

    /**
     * Constructs a {@code XmlAdaptedscoreList} with the given {@code tagName}.
     */
    public XmlAdaptedScoreList(List<XmlAdaptedTest> testList) {

        if (testList != null) {
            this.scoreList = new ArrayList<>(testList);
        }
    }

    /**
     * Converts a given scoreList into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedScoreList(ScoreList source) {
        scoreList = source.getScoreList().stream()
                .map(XmlAdaptedTest::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public ScoreList toModelType() throws IllegalValueException {

        final List<Test> scoreListTest = new ArrayList<>();
        for (XmlAdaptedTest test : scoreList) {
            scoreListTest.add(test.toModelType());
        }
        final Set<Test> modelTest = new HashSet<>(scoreListTest);
        return new ScoreList(modelTest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedScoreList)) {
            return false;
        }

        return scoreList.equals(((XmlAdaptedScoreList) other).scoreList);
    }
}

