package RouletPck;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that displays an image as its background and can be repositioned.
 * Used for panels that contain buttons or other components with a background image.
 */
public class PanelForButtons extends JPanel {
    private String imgPath;
    private Image img;
    private int x, y;
    Components components;

    /**
     * Constructs a PanelForButtons with the specified image path, position, section (unused),
     * and components reference.
     *
     * @param imgPath   the file path of the background image
     * @param x         the x-coordinate for the panel's position
     * @param y         the y-coordinate for the panel's position
     * @param section   a string identifier for the section (currently unused)
     * @param components a Components object associated with this panel
     */
    public PanelForButtons(String imgPath, int x, int y, String section, Components components) {
        this.imgPath = imgPath;
        this.components = components;
        this.x = x;
        this.y = y;
        this.img = new ImageIcon(imgPath).getImage();
        this.setLayout(null);
        this.setBounds(x, y, 500, 500);
        this.setVisible(true);
        this.setOpaque(true);
        this.setBackground(new Color(7, 77, 35));
    }

    /**
     * Paints the component by drawing the background image stretched to fit the panel.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (img != null) {
            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Changes the background image of the panel to the image located at the new path.
     * Automatically repaints the panel to reflect the new image.
     *
     * @param newPath the file path of the new background image
     */
    public void setImage(String newPath) {
        this.imgPath = newPath;
        this.img = new ImageIcon(newPath).getImage();
        repaint();
    }

    /**
     * Moves the panel to a new position on its container.
     * Updates the bounds and repaints the panel.
     *
     * @param newX the new x-coordinate of the panel
     * @param newY the new y-coordinate of the panel
     */
    public void movePanel(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        this.setBounds(x, y, 500, 500);
        repaint();
        revalidate();
    }
}
