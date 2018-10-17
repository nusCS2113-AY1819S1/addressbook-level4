package seedu.address.ui;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class SubmitBox {

    static boolean isYes;

    public static boolean display(String title, String message) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.resizableProperty().setValue(false);
        window.setMinWidth(300);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            window.close();
            isYes = true;
        });
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            window.close();
            isYes = false;
        });
        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return isYes;
    }
}
