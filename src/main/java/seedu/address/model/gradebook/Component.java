package seedu.address.model.gradebook;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Objects;

public class Component {

    private final Name name;
    private final MaxMarks maxMarks;
    private final Weightage weightage;

    public Component(Name name, MaxMarks maxMarks, Weightage weightage) {
        requireAllNonNull(name,maxMarks,weightage);
        this.name = name;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }

    public Name getName() {
        return name;
    }

    public MaxMarks getMaxMarks() {
        return maxMarks;
    }

    public Weightage getWeightage() {
        return weightage;
    }

    public boolean isSameComponent(Component otherComponent) {
        if (otherComponent == this) {
            return true;
        }

        return otherComponent != null
                && otherComponent.getName().equals(getName())
                && (otherComponent.getMaxMarks().equals(getMaxMarks())
                || otherComponent.getWeightage().equals(getWeightage()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Component)) {
            return false;
        }
        Component otherComponent = (Component) other;
        return otherComponent.getName().equals(getName())
                && otherComponent.getMaxMarks().equals(getMaxMarks())
                && otherComponent.getMaxMarks().equals(getWeightage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxMarks, weightage);
    }

    @Override
    public  String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Max. Marks ")
                .append(getMaxMarks())
                .append( "Weightage ")
                .append(getWeightage());
        return builder.toString();
    }
}
