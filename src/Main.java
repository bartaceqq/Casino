import PopUp.FramePopUpSmallRes;
import RouletPck.Components;
import MainMenicko.MainMenu;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        if (width < 1200 || height < 800) {
            new FramePopUpSmallRes();
        } else {

            MainMenu mainMenu = new MainMenu();
        }
    }
}
