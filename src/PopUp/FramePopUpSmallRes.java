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
// Load custom pixel font
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

        // Create a JLabel with the warning message, centered horizontally and vertically
        JLabel messageLabel = new JLabel(
                "<html><div style='text-align: center;'>Your resolution is too small.<br>" +
                        "Please change it to at least 1200 x 800.</div></html>",
                SwingConstants.CENTER
        );
        messageLabel.setFont(pixelFont);
        // Use a layout that centers the label both horizontally and vertically
        this.setLayout(new GridBagLayout());
        this.add(messageLabel);
        this.setVisible(true);
    }
}

