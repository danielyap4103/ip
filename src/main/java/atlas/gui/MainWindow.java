package atlas.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    private GuiUi guiUi = new GuiUi();

    @FXML
    public void initialize() {
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String greeting = guiUi.getResponse("");
        dialogContainer.getChildren().add(
                DialogBox.getAtlasDialog(greeting));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = guiUi.getResponse(input);

        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input));
        dialogContainer.getChildren().add(
                DialogBox.getAtlasDialog(response));

        userInput.clear();

        if (input.trim().equals("bye")) {
            Platform.exit();
        }
    }
}
