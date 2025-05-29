import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    JPanel mainPanel;
    public MainMenu() {
        this.setTitle("Casino");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        mainPanel = new BackgroundPanel("src/Images/MainBackground.png");
        mainPanel.setBackground(Color.BLACK);
        mainPanel.addMouseListener(new MainPanelList(MainMenu.this));
        this.add(mainPanel);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
    }
}
