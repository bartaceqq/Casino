import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BlackJack extends JFrame {
    private JPanel mainpanel;
    public int playercount = 0;
    private JLabel showinfoLabel;
    private PlayerState[] players;
    private int currentPlayerToBet = 0;
    private int totalPlayers = 0;
    private BufferedImage[][] cards = new BufferedImage[13][4]; // 13 ranks x 4 suits

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
    private boolean betted = false;
    ButtonSetup buttonSetup = new ButtonSetup();

    public BlackJack() {
        setTitle("Black Jack");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setupFont();
        setupMainPanel();
        mainpanel.setLayout(null);
        SetupButtons();
        add(mainpanel);

        SetupCards setupCards = new SetupCards();
        setupCards.extractFiles(cards); // <<=== UPDATED to use grid
        setVisible(true);

        new PopUp(this, "How many players are playing?", this);
    }

    public void setuppositions() {
        totalPlayers = playercount;
        players = new PlayerState[playercount];

        switch (playercount) {
            case 1 -> players[0] = new PlayerState(firstplayer);
            case 2 -> {
                players[0] = new PlayerState(firstplayer);
                players[1] = new PlayerState(thirdplayer);
            }
            case 3 -> {
                players[0] = new PlayerState(firstplayer);
                players[1] = new PlayerState(secondplayer);
                players[2] = new PlayerState(thirdplayer);
            }
        }
    }

    private void setupMainPanel() {
        mainpanel = new BackgroundPanel("src/Images/BlackJack/blackjack_table.png");
    }

    public void setupFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf")).deriveFont(80f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetupButtons() {
        betlabel = buttonSetup.setupbetlabel(pixel, mainpanel);
        addbet = buttonSetup.setupaddbutton(pixel, mainpanel);
        lessbet = buttonSetup.setuplessbutton(pixel, mainpanel);
        showinfoLabel = buttonSetup.setupinfolabel(pixel, mainpanel);
        showinfoLabel.setText("Player 1 is betting");

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
                } else {
                    buttonSetup.changeinfolabel(showinfoLabel, "Player " + (currentPlayerToBet + 1) + " is betting", this);
                }
            }
        });
    }

    private void dealInitialCards() {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].hasCard && players[i].hasBet) {
                int rank = (int) (Math.random() * 13); // 0–12
                int suit = (int) (Math.random() * 4);  // 0–3

                BufferedImage img = cards[rank][suit];
                if (img != null) {
                    JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
                    label.setBounds(players[i].position.x, players[i].position.y, 128, 128);
                    mainpanel.add(label);
                    mainpanel.repaint();
                    players[i].hasCard = true;
                    players[i].cardnum = rank + 2; // Card value from 2 to A
                    System.out.println("Dealt card to player " + (i + 1) + ": Rank " + (rank + 2) + ", Suit " + suit);
                }
            }
        }
    }

    public void testthecardlabel(String path) {
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
