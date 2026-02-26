package atlas.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DialogBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    public DialogBox() {}

    private void setText(String text) {
        dialog.setText(text);
    }

    private void setImage(Image img) {
        displayPicture.setImage(img);
    }

    private static HBox load(String text, Image img, boolean isUser) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    DialogBox.class.getResource("/view/DialogBox.fxml"));
            HBox box = loader.load();

            DialogBox controller = loader.getController();
            controller.setText(text);
            controller.setImage(img);

            if (isUser) {
                box.setAlignment(Pos.TOP_RIGHT);
                box.getChildren().setAll(
                        controller.dialog,
                        controller.displayPicture
                );
            } else {
                box.setAlignment(Pos.TOP_LEFT);
            }

            return box;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HBox getUserDialog(String text) {
        Image userImg = new Image(
                DialogBox.class.getResourceAsStream("/images/user.png"));
        return load(text, userImg, true);
    }

    public static HBox getAtlasDialog(String text) {
        Image atlasImg = new Image(
                DialogBox.class.getResourceAsStream("/images/atlas.png"));
        return load(text, atlasImg, false);
    }
}

