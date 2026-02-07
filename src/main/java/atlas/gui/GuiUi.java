package atlas.gui;

import atlas.Atlas;
import atlas.ui.Ui;

public class GuiUi {

    private Atlas atlas;

    public GuiUi() {
        Ui ui = new Ui();
        atlas = new Atlas(ui);
    }

    public String getResponse(String input) {
        return atlas.getResponse(input);
    }
}
