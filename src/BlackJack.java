import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class BlackJack extends JFrame {
    private JPanel mainpanel;
    public int playercount = 0;
    private JLabel showinfoLabel;
    private PlayerState[] players;
    private int currentPlayerToBet = 0;
    private int totalPlayers = 0;
    int maxRank = 11; // ranks 2–11 (where 10 holds J, Q, K, and 11 is Ace)
    int maxSuit = 4;  // suits 1–4 (C, D, H, S)

    BufferedImage[][] cards = new BufferedImage[maxRank + 1][maxSuit + 1];
    private Pointer firstplayer = new Pointer(142, 432);
    private Pointer secondplayer = new Pointer(530, 432);
    private Pointer thirdplayer = new Pointer(918, 432);
    private JButton hit;
    private JButton stay;
    private JButton addbet;
    private JButton lessbet;
    private JLabel betlabel;
    private JButton hitbutton;
    private JButton betbutton;
    private JButton staybutton;
    private Font pixel;
    private int playerplaying = 0;
    private boolean secondroundplaying = false;
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
        if (secondroundplaying){
            secondroundplay();
        }
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
        hitbutton = buttonSetup.setuphitbutton(pixel, mainpanel);
        hitbutton.setVisible(false);
        staybutton = buttonSetup.setupstaybutton(pixel, mainpanel);
        staybutton.setVisible(false);
        betbutton = new JButton("BET");
        betbutton.setFont(pixel);
        betbutton.setBounds(500, 700, 200, 50);
        mainpanel.add(betbutton);

        betbutton.addActionListener(e -> {
            if (currentPlayerToBet < totalPlayers) {
                players[currentPlayerToBet].hasBet = true;
                players[currentPlayerToBet].phase++;
                System.out.println("Player " + (currentPlayerToBet + 1) + " has bet.");
                currentPlayerToBet++;

                if (currentPlayerToBet == totalPlayers) {
                    //That change ensures dealInitialCards() runs after the GUI has finished processing the button click event(bug fix bcs it wasnt loading when bet clicked too fast)
                    SwingUtilities.invokeLater(this::dealInitialCards);
                }
                else {
                    buttonSetup.changeinfolabel(showinfoLabel, "Player " + (currentPlayerToBet + 1) + " is betting", this);
                }
            }
        });
    }

    public void secondround(){
      betbutton.setVisible(false);
      addbet.setVisible(false);
      lessbet.setVisible(false);
      hitbutton.setVisible(true);
      staybutton.setVisible(true);
      playerplaying = 1;
      changetext("player " + playerplaying + " is playing");
        secondroundplay();
    }
    public void secondroundplay(){
        hitbutton.addActionListener(e -> {

        });
    }
    public void changetext(String text){
    showinfoLabel.setText(text);
    mainpanel.repaint();
    }
    private void dealInitialCards() {
        System.out.println(players.length + " players length");

        for (int i = 0; i < players.length; i++) {
            if (!players[i].hasCard && players[i].hasBet) {
                System.out.println("passed");

                int rawRank = (int) (Math.random() * 13) + 1; // 1–13
                int rank;
                if (rawRank == 1) {
                    rank = 11; // Ace
                } else if (rawRank >= 11) {
                    rank = 10; // J, Q, K
                } else {
                    rank = rawRank; // 2–10
                }

                int suit = (int) (Math.random() * 4) + 1; // 1–4

                BufferedImage img = cards[rank][suit];

                if (img != null) {
                    System.out.println("second pass");

                    JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
                    label.setBounds(players[i].position.x, players[i].position.y, 128, 128);
                    mainpanel.add(label);
                    mainpanel.repaint();

                    players[i].hasCard = true;
                    players[i].value += rank; // remember: Ace is 11, face cards are 10

                    System.out.println("Dealt card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
                } else {
                    System.out.println("error while loading card: [rank] = " + rank + ", suit = " + suit);
                }
            }
        }
        secondround();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlackJack::new);
    }
}
