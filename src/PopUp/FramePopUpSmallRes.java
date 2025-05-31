package PopUp;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FramePopUpSmallRes extends JFrame {
    Font pixelFont;

    /**
     * Constructs a small popup frame with a warning message about screen resolution.
     * The frame is 100x100 pixels and appears centered on the screen.
     */
    public FramePopUpSmallRes() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf"))
                    .deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setTitle("PopUp Small Res");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null); // Center on screen
        JLabel messageLabel = new JLabel(
                "Your resolution is too small.<br>" +
                        "Please change it to at least 1200 x 800.",
                SwingConstants.CENTER
        );
        messageLabel.setFont(pixelFont);
        this.setLayout(new GridBagLayout());
        this.add(messageLabel);
        this.setVisible(true);
    }
}

