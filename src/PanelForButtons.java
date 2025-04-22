import javax.swing.*;
import java.awt.*;

public class PanelForButtons extends JPanel {
    private String imgPath;
    private Image img;
    private int x, y;
    Components components;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (img != null) {
            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // ✅ Updated method to move and repaint the panel
    public void setImage(String newPath) {
        this.imgPath = newPath;
        this.img = new ImageIcon(newPath).getImage();
        repaint();
    }

    // ✅ New method to move the panel
    public void movePanel(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        this.setBounds(x, y, 500, 500);  // Update panel position
        repaint();
        revalidate();
    }
}
