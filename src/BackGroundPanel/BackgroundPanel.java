package BackGroundPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that displays a background image scaled to fit the panel size.
 * <p>
 * The image is loaded from the specified file path, and the panel's preferred size is
 * set to the dimensions of the image. The image is painted to fill the entire panel.
 * </p>
 */
public class BackgroundPanel extends JPanel {
    /**
     * The background image to be drawn on the panel.
     */
    private Image backgroundImage;

    /**
     * Constructs a BackgroundPanel with the image located at the specified path.
     *
     * @param imagePath the file path to the background image.
     */
    public BackgroundPanel(String imagePath) {
        // Load the image
        backgroundImage = new ImageIcon(imagePath).getImage();
        if (backgroundImage == null) {
            System.out.println("Background image is null");
        }
        // Optional: set size of panel to image size
        this.setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));
    }

    /**
     * Overrides the paintComponent method to draw the background image scaled to fit the panel.
     *
     * @param g the Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
