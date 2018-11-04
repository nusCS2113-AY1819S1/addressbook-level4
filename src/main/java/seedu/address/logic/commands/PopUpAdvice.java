//@@author SHININGGGG
package seedu.address.logic.commands;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;

public class PopUpAdvice {

    private final String content;

    PopUpAdvice(String x) {
        content = x;
    }

    public void popup() {
        Stage stage = new Stage();
        Label label = new Label();
        label.setText(content);
        label.setFont(Font.font(16));
        HBox layout = new HBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().add(label);
        stage.setScene(new Scene(layout));
        stage.show();
        //System.out.println("Pop up a window for advice");
    }
}
