import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class BlackJack extends JFrame {
    private JPanel mainpanel;
    public int playercount = 0;
    private JLabel showinfoLabel;
    public PlayerState[] players;
    public int currentPlayerToBet = 0;
    private int totalPlayers = 0;
    int maxRank = 11;
    int maxSuit = 4;
    private int extended1 =0;
    private int extended2 =0;
    private boolean firstrun = true;
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
    private MoneyLoaderBlackJack moneyloader = new MoneyLoaderBlackJack();
    private int playerplaying = 0;
    private int playertochoose = 0;
    private boolean secondroundplaying = false;
    private boolean betted = false;
    ButtonSetup buttonSetup = new ButtonSetup();
    private PlayerState dealer;
    private Pointer dealerPosition = new Pointer(530, 100); // middle top

    public int roundcounter = 1;
    private int roundcounterd = 1;
    private int counter = 0;
    public void runMainMethod(){
        setTitle("Black Jack");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setupFont();
        setupMainPanel();
        mainpanel.setLayout(null);

        add(mainpanel);

        SetupCards setupCards = new SetupCards();
        setupCards.extractFiles(cards);
        setVisible(true);
    }
    public BlackJack(boolean firstrun) {
        this.firstrun = firstrun;

       runMainMethod();
        switch (totalPlayers){
            case 1:
                moneyloader.loadmoney(firstrun, players[0].money,extended1, extended2);
                break;
                case 2:
                    moneyloader.loadmoney(firstrun, players[0].money,players[1].money, extended2);
                    break;
                    case 3:
                        moneyloader.loadmoney(firstrun, players[0].money,players[1].money, players[2].money);
                        break;
        }
        SetupButtons();
        new PopUp(this, "How many players are playing?", this);
        if (firstrun) {
            moneyloader.loadmoney(true, 0, 0, 0); // actual values are overwritten
        } else {
            moneyloader.loadmoney(false, players[0].money, players[1].money, players[2].money);
        }

// Now read the money values into players
        switch (playercount) {
            case 1:
                moneyloader.moneyread(players[0], null, null);
                break;
            case 2:
                moneyloader.moneyread(players[0], players[1], null);
                break;
            case 3:
                moneyloader.moneyread(players[0], players[1], players[2]);
                break;
        }
        changebetlabeltext();
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

        dealer = new PlayerState(dealerPosition);
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
            player.hashitstay = false;
            player.hassecondcard = false;
        }
        playertochoose = 0;
        secondround();
    }

    public void SetupButtons() {
        betlabel = buttonSetup.setupbetlabel(pixel, mainpanel);
        addbet = buttonSetup.setupaddbutton(pixel, mainpanel, BlackJack.this);
        lessbet = buttonSetup.setuplessbutton(pixel, mainpanel, BlackJack.this);
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
                    dealInitialCards(true);
                } else {
                    buttonSetup.changeinfolabel(showinfoLabel, "Player " + (currentPlayerToBet + 1) + " is betting", this);
                }
            }
            changebetlabeltext();
            updateability();
            whowins();
        });

        hitbutton.addActionListener(e -> {

            if (playertochoose < totalPlayers && !players[playertochoose].hashitstay) {
                players[playertochoose].hit = true;
                players[playertochoose].stay = false;
                players[playertochoose].hashitstay = true;

                System.out.println("Player " + (playertochoose + 1) + " chose HIT");

                playertochoose++;
                if (playertochoose == totalPlayers) {
                    dealInitialCards(false);
                } else {
                    changetext("Player " + (playertochoose + 1) + " is playing");
                }
            }
            updateability();
            whowins();

        });

        staybutton.addActionListener(e -> {
            if (playertochoose < totalPlayers && !players[playertochoose].hashitstay) {
                players[playertochoose].hit = false;
                players[playertochoose].stay = true;
                players[playertochoose].hashitstay = true;

                System.out.println("Player " + (playertochoose + 1) + " chose STAY");

                playertochoose++;
                if (playertochoose == totalPlayers) {
                    dealInitialCards(false);
                } else {
                    changetext("Player " + (playertochoose + 1) + " is playing");
                }
            }
            updateability();
        });
    }

    public void secondround() {
        betbutton.setVisible(false);
        betlabel.setVisible(false);
        addbet.setVisible(false);
        lessbet.setVisible(false);
        hitbutton.setVisible(true);
        staybutton.setVisible(true);
        playertochoose = 0;
        changetext("Player " + (playertochoose + 1) + " is playing");
        updateability();
    }

    public void dealerround() {
        Random rand = new Random();
        int randomizer = rand.nextInt(100);

        if (dealer.value <= 12) {
            dealer.hit = true;
            dealer.stay = false;
        } else if (dealer.value < 17) {
            if (randomizer < 50) {
                dealer.hit = true;
                dealer.stay = false;
            } else {
                dealer.hit = false;
                dealer.stay = true;
                dealer.playerisdone = true;
            }
        } else {
            dealer.hit = false;
            dealer.stay = true;
            dealer.playerisdone = true;
        }
    }

    public void updateability() {
        boolean isDone = players[playertochoose].stay || players[playertochoose].value >= 21;
        players[playertochoose].playerisdone = isDone;

        hitbutton.setEnabled(!players[playertochoose].stay && players[playertochoose].value < 21);
    }

public void changebetlabeltext(){
    System.out.println("prosloooooooooooooooo");
    try {
        if (currentPlayerToBet < totalPlayers) {
            int currentbet = players[currentPlayerToBet].bet;

            betlabel.setText(String.valueOf(currentbet));
        }
    }catch (Exception e){
        e.printStackTrace();
    }
}
    public void changetext(String text) {
        betlabel.setText(String.valueOf(players[currentPlayerToBet -1].value));
        showinfoLabel.setText(text);
        mainpanel.repaint();
    }

    private void dealInitialCards(boolean betting) {
        counter++;
        playertochoose = 0;

        if (!dealer.hasBet) {
            dealCardToDealer();
            dealer.hasBet = true;
        } else {
            dealerround(); // Moved BEFORE checking dealer.hit
            if (dealer.hit) {
                dealCardToDealer();
            }
        }

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
            } else if (!betting && !players[i].hassecondcard && players[i].hashitstay && players[i].hit) {
                int yOffset = (-30) * counter;
                int xOffset = (-30) * counter;
                label.setBounds(x + xOffset, y + yOffset, cardWidth, cardHeight);
                players[i].hassecondcard = true;
                players[i].value += rank;
                System.out.println("Dealt second card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
            } else {
                continue;
            }

            roundcounter++;
            mainpanel.add(label);
            mainpanel.repaint();
        }

        for (int i = 0; i < players.length; i++) {
            System.out.println("Player " + (i + 1) + " has value: " + players[i].value);
        }
    whowins();
        resetRound();
    }

    private void dealCardToDealer() {
        int rawRankd = (int) (Math.random() * 13) + 1;
        int rankd = (rawRankd == 1) ? 11 : (rawRankd >= 11) ? 10 : rawRankd;
        int suitd = (int) (Math.random() * 4) + 1;

        BufferedImage imgd = cards[rankd][suitd];
        if (imgd == null) {
            System.out.println("error while loading card: [rank] = " + rankd + ", suit = " + suitd);
        }

        dealer.value += rankd;
        int cardWidthd = 128;
        int cardHeightd = 128;
        int yOffsetd = (-20) * roundcounterd;
        int xOffsetd = (-20) * roundcounterd;
        int xd = dealerPosition.x;
        int yd = dealerPosition.y;

        JLabel labeld = new JLabel(new ImageIcon(imgd.getScaledInstance(cardWidthd, cardHeightd, Image.SCALE_SMOOTH)));
        labeld.setBounds(xd + xOffsetd, yd + yOffsetd, cardWidthd, cardHeightd);
        mainpanel.add(labeld);
        mainpanel.repaint();
        roundcounterd++;
    }private void fullResetRound() {

        switch (totalPlayers){
            case 1:
                moneyloader.moneyread(players[0],null, null);
                break;
            case 2:
                moneyloader.moneyread(players[0],players[1], null);
                break;
            case 3:
                moneyloader.moneyread(players[0],players[1], players[2]);
                break;
        }
        BlackJack blackjack = new BlackJack(false);
        BlackJack.this.dispose();

    }

    public void whowins(){
        System.out.println();
        boolean done = true;
    for (PlayerState playerstate : players) {
        if (!playerstate.playerisdone){
            done = false;
        }
    }
    if (done) {
        System.out.println("restartttttttttttttttttttttt");
        fullResetRound();
        mainpanel.repaint();
    }
    }
    public static void main(String[] args) {
        BlackJack blackjack = new BlackJack(true);
    }
}
