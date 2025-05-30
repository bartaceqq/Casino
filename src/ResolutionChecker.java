import MainMenicko.MainMenu;
import PopUp.FramePopUpSmallRes;

import java.awt.*;
import java.awt.event.ActionListener;

public class ResolutionChecker implements Runnable {
    public boolean resolution(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        if (width < 1200 || height < 800) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void run() {
        if (resolution()) {
            MainMenu mainMenu = new MainMenu();
        } else {
            FramePopUpSmallRes framePopUpSmallRes = new FramePopUpSmallRes();
        }
    }
}
