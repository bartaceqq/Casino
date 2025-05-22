import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BlackJack extends JFrame {
    private JPanel mainpanel;
    private File[] cardfiles;
    public int playercount=0;
    private PlayerState[] players;
    private int currentPlayerToBet = 0;
    private int totalPlayers = 0;
    private HashMap<Integer, BufferedImage> cards; // Store card images with integer keys
    private Pointer firstplayer = new Pointer(142, 432);
    private Pointer secondplayer = new Pointer(530, 432);
    private Pointer thirdplayer = new Pointer(918, 432);
    private JButton hit;
    private JButton stay;
    private JButton addbet;
    private JButton lessbet;
    private JLabel betlabel;
    private JButton betbutton;
    private Font pixel;
    private int dealing = 0;
    private boolean betted = false;
    ButtonSetup buttonSetup = new ButtonSetup();
    public BlackJack() {
        setTitle("Black Jack");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cards = new HashMap<>(); // ✅ Initialize HashMap
        setupFont();
        setupMainPanel();
        mainpanel.setLayout(null);
        SetupButtons();
        add(mainpanel);

        extractFiles(); // ✅ Load images into the HashMap
        System.out.println("Total loaded cards: " + cards.size());

        setVisible(true); // ✅ Show window after all setup
        new PopUp(BlackJack.this, "How many players are playing?", BlackJack.this);

    }
    public void setuppositions() {
        totalPlayers = playercount;
        players = new PlayerState[playercount];

        switch (playercount) {
            case 1:
                players[0] = new PlayerState(firstplayer);
                break;
            case 2:
                players[0] = new PlayerState(firstplayer);
                players[1] = new PlayerState(thirdplayer);
                break;
            case 3:
                players[0] = new PlayerState(firstplayer);
                players[1] = new PlayerState(secondplayer);
                players[2] = new PlayerState(thirdplayer);
                break;
        }
    }

    private void setupMainPanel() {
        // This assumes you have a BackgroundPanel class handling background images
        mainpanel = new BackgroundPanel("src/Images/BlackJack/blackjack_table.png");
    }
    public void setupFont(){
        try {


            //chat
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf"))
                    .deriveFont(80f); // Set the font size (can be smaller/larger as needed)

            // Register the font
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixel);
            //konec chata
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SetupButtons() {
        buttonSetup.setupbetlabel(betlabel, pixel, mainpanel);
        buttonSetup.setupaddbutton(addbet, pixel, mainpanel);
        buttonSetup.setuplessbutton(lessbet, pixel, mainpanel);

        // Create BET button here
        betbutton = new JButton("BET");
        betbutton.setFont(pixel);
        betbutton.setBounds(550, 700, 200, 50);
        mainpanel.add(betbutton);

        betbutton.addActionListener(e -> {
            if (currentPlayerToBet < totalPlayers) {
                players[currentPlayerToBet].hasBet = true;
                System.out.println("Player " + (currentPlayerToBet + 1) + " has bet.");
                currentPlayerToBet++;

                if (currentPlayerToBet == totalPlayers) {
                    dealInitialCards();
                }
            }
        });
    }


    private void dealInitialCards() {
        //chat gipiti metodicek
        for (int i = 0; i < players.length; i++) {
            if (!players[i].hasCard) {
                int randomCardIndex = (int) (Math.random() * cards.size());
                BufferedImage img = cards.get(randomCardIndex);
                if (img != null) {
                    JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
                    label.setBounds(players[i].position.x, players[i].position.y, 128, 128);
                    mainpanel.add(label);
                    mainpanel.repaint();
                    players[i].hasCard = true;
                    System.out.println("Dealt card to player " + (i + 1));
                }
            }
        }
    }
// chat method modified by me
    private void extractFiles() {
        //
        File folder = new File("src/Images/BlackJack/Cards");

        // Debug output to check if the path is valid
        System.out.println("Looking in: " + folder.getAbsolutePath());
        System.out.println("Exists? " + folder.exists());
        System.out.println("Is directory? " + folder.isDirectory());

        cardfiles = folder.listFiles();

        if (cardfiles != null) {
            int index = 0;
            for (File file : cardfiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    try {
                        BufferedImage cardImage = ImageIO.read(file);
                        cards.put(index++, cardImage);
                        System.out.println("Loaded: " + file.getName());
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
    // testing method if label works fine + i needed to find x-y for labels
    public void testthecardlabel(String path){
        JLabel cardlabel = new JLabel();
       cardlabel.setIcon(getScaledCardIcon("src/Images/BlackJack/Cards/card_clubs_08.png", 128, 128));
       cardlabel.setBounds(918, 432, 128, 128);
       mainpanel.add(cardlabel);

    }
    private ImageIcon getScaledCardIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlackJack::new);
    }
}
