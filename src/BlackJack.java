import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class BlackJack extends JFrame {
    private JPanel mainpanel;
    public int playercount = 0;
    private JLabel showinfoLabel;
    private PlayerState[] players;
    private int currentPlayerToBet = 0;
    private int totalPlayers = 0;
    int maxRank = 11;
    int maxSuit = 4;

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
    private int playertochoose = 0;
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
        setupCards.extractFiles(cards);
        setVisible(true);

        new PopUp(this, "How many players are playing?", this);
        if (secondroundplaying) {
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
    private void resetRound() {
        for (PlayerState player : players) {
            player.hit = false;
            player.stay = false;
            player.hashitstay = false;
            player.hassecondcard = false;
            // Optionally reset hasCard/value if you want a new hand:
            // player.hasCard = false;
            // player.value = 0;
        }

        playertochoose = 0;
        secondround(); // Show hit/stay again
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
                    dealInitialCards(0, true);
                } else {
                    buttonSetup.changeinfolabel(showinfoLabel, "Player " + (currentPlayerToBet + 1) + " is betting", this);
                }
            }
        });

        // ✅ Moved from secondroundplay – only added ONCE
        hitbutton.addActionListener(e -> {
            if (playertochoose < totalPlayers && !players[playertochoose].hashitstay) {
                players[playertochoose].hit = true;
                players[playertochoose].stay = false;
                players[playertochoose].hashitstay = true;

                System.out.println("Player " + (playertochoose + 1) + " chose HIT");

                playertochoose++;
                if (playertochoose == totalPlayers) {
                    dealInitialCards(1, false);
                } else {
                    changetext("Player " + (playertochoose + 1) + " is playing");
                }
            }
        });

        staybutton.addActionListener(e -> {
            if (playertochoose < totalPlayers && !players[playertochoose].hashitstay) {
                players[playertochoose].hit = false;
                players[playertochoose].stay = true;
                players[playertochoose].hashitstay = true;

                System.out.println("Player " + (playertochoose + 1) + " chose STAY");

                playertochoose++;
                if (playertochoose == totalPlayers) {
                    dealInitialCards(1, false);
                } else {
                    changetext("Player " + (playertochoose + 1) + " is playing");
                }
            }
        });
    }

    public void secondround() {
        betbutton.setVisible(false);
        addbet.setVisible(false);
        lessbet.setVisible(false);
        hitbutton.setVisible(true);
        staybutton.setVisible(true);
        playertochoose = 0;
        changetext("Player " + (playertochoose + 1) + " is playing");
    }

    public void secondroundplay() {
        // now empty (listener logic is in SetupButtons)
    }

    public void changetext(String text) {
        showinfoLabel.setText(text);
        mainpanel.repaint();
    }

    private void dealInitialCards(int doubler, boolean betting) {
        System.out.println(players.length + " players length");
        playertochoose = 0;
        for (int i = 0; i < players.length; i++) {
            int rawRank = (int) (Math.random() * 13) + 1;
            int rank = (rawRank == 1) ? 11 : (rawRank >= 11) ? 10 : rawRank;
            int suit = (int) (Math.random() * 4) + 1;

            BufferedImage img = cards[rank][suit];
            if (img == null) {
                System.out.println("error while loading card: [rank] = " + rank + ", suit = " + suit);
                continue;
            }

            int cardWidth = 128;
            int cardHeight = 128;

            int x = players[i].position.x;
            int y = players[i].position.y;

            JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));

            if (betting && !players[i].hasCard && players[i].hasBet) {
                label.setBounds(x, y, cardWidth, cardHeight);
                players[i].hasCard = true;
                players[i].value += rank;
                System.out.println("Dealt card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
            } else if (!betting && !players[i].hassecondcard && players[i].hashitstay) {
                if (players[i].hit) {
                    int yOffset = -30;
                    int xOffset = 30;
                    label.setBounds(x + xOffset, y + yOffset, cardWidth, cardHeight);
                    players[i].hassecondcard = true;
                    players[i].value += rank;
                    System.out.println("Dealt second card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
                }
            } else {
                continue;
            }

            mainpanel.add(label);
            mainpanel.repaint();
        }

        for (int i = 0; i < players.length; i++) {
            System.out.println("player " + (i + 1) + " has value: " + players[i].value);
        }

        secondround();
        resetRound();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlackJack::new);
    }
}
