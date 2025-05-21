import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackJack extends JFrame {
    JPanel mainpanel;

    public BlackJack() {
        this.setTitle("Black Jack");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        setupmainpanel();
        this.add(mainpanel);

    }
    public void setupmainpanel(){
        mainpanel = new BackgroundPanel("src/Images/BlackJack/blackjack_table.jpg");
    }
    //extractim files
    public void extractfiles(){
        File folder = new File("cards"); // Path to the folder
        File[] files = folder.listFiles(); // List all files

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    try {
                        BufferedImage cardImage = ImageIO.read(file);
                        System.out.println("Loaded: " + file.getName());
                        // You can now use `cardImage` in your game logic
                    } catch (IOException e) {
                        System.out.println("Error loading " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Folder not found or is empty.");
        }

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlackJack::new);
    }
}
