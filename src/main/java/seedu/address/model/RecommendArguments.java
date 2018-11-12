package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Mode;

/**
 * Represents a Recommend arguments in the workout book.
 */
public class RecommendArguments {

    private Optional<Calories> calories;
    private Optional<Difficulty> difficulty;
    private Optional<Duration> duration;

    private Optional<Boolean> caloriesOptionality;
    private Optional<Boolean> difficultyOptionality;
    private Optional<Boolean> durationOptionality;

    private Optional<Mode> mode;

    /**
     * Builder pattern
     */
    public static class Builder {

        private Optional<Calories> calories = Optional.empty();
        private Optional<Difficulty> difficulty = Optional.empty();
        private Optional<Duration> duration = Optional.empty();

        private Optional<Boolean> caloriesOptionality = Optional.empty();
        private Optional<Boolean> difficultyOptionality = Optional.empty();
        private Optional<Boolean> durationOptionality = Optional.empty();

        private Optional<Mode> mode = Optional.empty();

        public Builder() {
        }

        /**
         * Calories setter for Builder
         */
        public Builder withCalories(Optional<Calories> calories, Optional<Boolean> optionality) {
            this.calories = calories;
            this.caloriesOptionality = optionality;
            return this;
        }

        /**
         * Difficulty setter for Builder
         */
        public Builder withDifficulty(Optional<Difficulty> difficulty, Optional<Boolean> optionality) {
            this.difficulty = difficulty;
            this.difficultyOptionality = optionality;
            return this;
        }

        /**
         * Duration setter for Builder
         */
        public Builder withDuration(Optional<Duration> duration, Optional<Boolean> optionality) {
            this.duration = duration;
            this.durationOptionality = optionality;
            return this;
        }

        /**
         * Mode setter for Builder
         */
        public Builder withMode(Optional<Mode> mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Main constructor for RecommendArguments
         */
        public RecommendArguments build() {
            RecommendArguments recommendArguments = new RecommendArguments();
            recommendArguments.setCalories(this.calories, this.caloriesOptionality);
            recommendArguments.setDifficulty(this.difficulty, this.difficultyOptionality);
            recommendArguments.setDuration(this.duration, this.durationOptionality);
            recommendArguments.setMode(this.mode);
            return recommendArguments;
        }
    }

    private RecommendArguments() {
    }

    /**
     * Returns true if both recommend arguments have the same identity and data fields.
     * This defines a stronger notion of equality between two recommend arguments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RecommendArguments)) {
            return false;
        }

        RecommendArguments otherRecommendArguments = (RecommendArguments) other;

        return (((otherRecommendArguments.isCaloriesNull() && isCaloriesNull())
                || ((!otherRecommendArguments.isCaloriesNull() && !isCaloriesNull())
                && (otherRecommendArguments.getCalories().equals(getCalories())
                && otherRecommendArguments.getCaloriesOptionality().equals(getCaloriesOptionality()))))
                && ((otherRecommendArguments.isDifficultyNull() && isDifficultyNull())
                || ((!otherRecommendArguments.isDifficultyNull() && !isDifficultyNull())
                && (otherRecommendArguments.getDifficulty().equals(getDifficulty())
                && otherRecommendArguments.getDifficultyOptionality().equals(getDifficultyOptionality()))))
                && ((otherRecommendArguments.isDurationNull() && isDurationNull())
                || ((!otherRecommendArguments.isDurationNull() && !isDurationNull())
                && (otherRecommendArguments.getDuration().equals(getDuration())
                && otherRecommendArguments.getDurationOptionality().equals(getDurationOptionality()))))
                && ((otherRecommendArguments.isModeNull() && isModeNull())
                || ((!otherRecommendArguments.isModeNull() && !isModeNull())
                && (otherRecommendArguments.getMode().equals(getMode())))));
    }

    public Calories getCalories() {
        return calories.get();
    }

    public Difficulty getDifficulty() {
        return difficulty.get();
    }

    public Duration getDuration() {
        return duration.get();
    }

    public Mode getMode() {
        return mode.get();
    }

    public Boolean getCaloriesOptionality() {
        return caloriesOptionality.get();
    }

    public Boolean getDifficultyOptionality() {
        return difficultyOptionality.get();
    }

    public Boolean getDurationOptionality() {
        return durationOptionality.get();
    }

    public void setCalories(Optional<Calories> calories, Optional<Boolean> optionality) {
        this.calories = calories;
        this.caloriesOptionality = optionality;
    }

    public void setDifficulty(Optional<Difficulty> difficulty, Optional<Boolean> optionality) {
        this.difficulty = difficulty;
        this.difficultyOptionality = optionality;
    }

    public void setDuration(Optional<Duration> duration, Optional<Boolean> optionality) {
        this.duration = duration;
        this.durationOptionality = optionality;
    }

    public void setMode(Optional<Mode> mode) {
        this.mode = mode;
    }

    public boolean isCaloriesNull() {
        return !calories.isPresent();
    }

    public boolean isDifficultyNull() {
        return !difficulty.isPresent();
    }

    public boolean isDurationNull() {
        return !duration.isPresent();
    }

    public boolean isModeNull() {
        return !mode.isPresent();
    }

    public ArrayList<Boolean> getOptionalsList() {
        return new ArrayList<>(List.of(caloriesOptionality.get(), difficultyOptionality.get(),
                durationOptionality.get()));
    }

    public int getTotalOptionals() {
        int totalOptionals = 0;
        ArrayList<Boolean> optionalsList = getOptionalsList();
        for (int i = 0; i < optionalsList.size(); i++) {
            if (optionalsList.get(i)) {
                totalOptionals++;
            }
        }
        return totalOptionals;
    }

}
