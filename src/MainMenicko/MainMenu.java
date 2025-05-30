package MainMenicko;

import javax.swing.*;
import java.awt.*;
import BackGroundPanel.BackgroundPanel;

/**
 * MainMenu class represents the main menu window for the Casino application.
 * It sets up the main JFrame with a background panel and listens for mouse events.
 */
public class MainMenu extends JFrame {

    /**
     * The main panel containing the background image and interactive elements.
     */
    JPanel mainPanel;

    /**
     * Constructs the main menu window.
     * Sets up the JFrame properties such as title, size, location, and adds
     * a background panel with a mouse listener.
     */
    public MainMenu() {
        this.setTitle("Casino");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Initialize the main panel with a background image
        mainPanel = new BackgroundPanel("src/Images/MainBackground.png");

        // Set panel background color to black (can be overridden by image)
        mainPanel.setBackground(Color.BLACK);

        // Add mouse listener to handle interactions on the main panel
        mainPanel.addMouseListener(new MainPanelList(MainMenu.this));

        // Add the panel to the JFrame
        this.add(mainPanel);

        // Make the window visible
        this.setVisible(true);
    }

    /**
     * Main entry point for the Casino application.
     * Creates and shows the main menu window.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
    }
}
