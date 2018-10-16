package seedu.address.model.gradebook;

/**
 This class includes all necessary validation for gradebook objects.
 */
public class Gradebook {
    private static final String FIND_MESSAGE_SUCCESS = "Successfully found!";
    /**
     This method checks if user inputs empty values for module code or gradebook component name.
     */
    public static boolean emptyParams (String moduleCode, String gradebookComponentName) {
        boolean empty = false;
        if (moduleCode.equals("") || gradebookComponentName.equals("")) {
            empty = true;
        }
        return empty;
    }

    /**
     This method checks if component already exists in Trajectory.
     */
    public static boolean duplicateComponent (String moduleCode, String gradebookComponentName) {
        boolean duplicate = false;
        if (GradebookManager.findGradebookComponent(moduleCode, gradebookComponentName)
                .feedbackToUser.contains(FIND_MESSAGE_SUCCESS)) {
            duplicate = true;
        }
        return duplicate;
    }

    /**
     This method checks if component has extra spaces between words.
     */
    public static boolean extraSpace (String gradebookComponentName) {
        boolean extraSpace = false;
        String gradebookComponentNameSubst = gradebookComponentName.replaceAll("\\s+", " ");
        if (!gradebookComponentName.equals(gradebookComponentNameSubst)) {
            extraSpace = true;
        }
        return extraSpace;
    }
}
