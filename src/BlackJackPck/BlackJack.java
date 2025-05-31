package BlackJackPck;

import BackGroundPanel.BackgroundPanel;
import MoneyLoaders.MoneyLoaderBlackJack;
import PopUp.PopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * Main class for the BlackJack game.
 * This class handles the GUI setup, player interactions, betting, card logic, and dealer actions.
 * It supports 1 to 3 players and includes betting functionality and state transitions between game phases.
 */
public class BlackJack extends JFrame {

    /** Exit button to quit the game */
    JButton exitbutton;

    /** Handles card dealing logic */
    CardDealing cardDealing = new CardDealing();

    /** The main panel containing all game components */
    public JPanel mainpanel;

    /** Current number of players */
    public int playercount = 0;

    /** Label to display info to the user */
    public JLabel showinfoLabel;

    /** Array of player states (up to 3 players) */
    public PlayerState[] players;

    /** The current player who is placing their bet */
    public int currentPlayerToBet = 0;

    /** Total number of players currently in the game */
    public int totalPlayers = 0;

    /** Maximum card rank (11 to accommodate Ace as 11) */
    int maxRank = 11;

    /** Maximum number of card suits */
    int maxSuit = 4;

    /** Timer (unused in this snippet, placeholder for animations) */
    Timer timer;

    /** Stores the winner names */
    String[] winners = new String[3];

    /** Animation-related variables (if used) */
    private int extended1 = 0, extended2 = 0;

    /** Flag to determine if it's the first time running the game */
    private boolean firstrun = true;

    /** 2D array representing card images */
    BufferedImage[][] cards = new BufferedImage[maxRank + 1][maxSuit + 1];

    /** Pointers for player card positions */
    private Pointer firstplayer = new Pointer(142, 432);
    private Pointer secondplayer = new Pointer(530, 432);
    private Pointer thirdplayer = new Pointer(918, 432);

    /** Button for player actions */
    private JButton hit, stay, addbet, lessbet, betbutton;

    /** Label to display the bet amount */
    private JLabel betlabel;

    /** Public buttons for other interactions */
    public JButton hitbutton, staybutton, continuebutton;

    /** Font used in the UI */
    private Font pixel;

    /** Labels to show each player's money */
    JLabel[] moneylabels = new JLabel[3];

    /** Utility for loading/saving money */
    public MoneyLoaderBlackJack moneyloader = new MoneyLoaderBlackJack();

    /** Index of the currently playing player */
    private int playerplaying = 0;

    /** Index of the player who is choosing to hit or stay */
    public int playertochoose = 0;

    /** Flag indicating whether second round (hit/stay phase) is in progress */
    private boolean secondroundplaying = false;

    /** Indicates if a player has placed a bet */
    private boolean betted = false;

    /** Utility for setting up UI buttons */
    ButtonSetup buttonSetup = new ButtonSetup();

    /** Dealer state */
    public PlayerState dealer;

    /** Position for the dealer on the table */
    public Pointer dealerPosition = new Pointer(530, 100);

    /** Round tracking counters */
    public int roundcounter = 1;
    public int roundcounterd = 1;
    public int counter = 0;

    /**
     * Constructor to create and launch the BlackJack game.
     * It initializes the GUI, shows player count popup, sets player money, and sets up buttons.
     *
     * @param firstrun Indicates whether it's the first game launch.
     * @param title    The title of the game window.
     */
    public BlackJack(boolean firstrun, String title) {
        this.firstrun = firstrun;
        runMainMethod(); // Sets up the JFrame and mainpanel

        new PopUp(this, "How many players are playing?", this);
        moneylabels = buttonSetup.monetlabels(players, totalPlayers, mainpanel);
        updatemoneylabels();

        for (JLabel label : moneylabels) {
            if (label != null) {
                System.out.println("je tam");
            }
        }

        System.out.println("is addbutton visible? " + addbet.isVisible());

        switch (playercount) {
            case 1 -> moneyloader.moneyread(players[0], null, null);
            case 2 -> moneyloader.moneyread(players[0], players[1], null);
            case 3 -> moneyloader.moneyread(players[0], players[1], players[2]);
        }

        this.setTitle(title);
        changebetlabeltext();
    }

    /**
     * Initializes the main game window, fonts, buttons, and card images.
     */
    public void runMainMethod() {
        setTitle("Black Jack");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setupFont();
        setupMainPanel();
        mainpanel.setLayout(null);
        SetupButtons();
        add(mainpanel);

        SetupCards setupCards = new SetupCards();
        setupCards.extractFiles(cards);

        setVisible(true);
    }

    /**
     * Updates money display labels for all players.
     */
    public void updatemoneylabels() {
        for (int i = 0; i < totalPlayers; i++) {
            moneylabels[i].setText("Money: $" + players[i].money);
        }
        mainpanel.revalidate();
        mainpanel.repaint();
    }

    /**
     * Sets player positions and initializes money based on player count.
     */
    public void setuppositions() {
        totalPlayers = playercount;
        players = new PlayerState[playercount];

        switch (playercount) {
            case 1 -> {
                players[0] = new PlayerState(secondplayer);
                players[0].name = "1";
            }
            case 2 -> {
                players[0] = new PlayerState(firstplayer);
                players[0].name = "1";
                players[1] = new PlayerState(thirdplayer);
                players[1].name = "2";
            }
            case 3 -> {
                players[0] = new PlayerState(firstplayer);
                players[0].name = "1";
                players[1] = new PlayerState(secondplayer);
                players[1].name = "2";
                players[2] = new PlayerState(thirdplayer);
                players[2].name = "3";
            }
        }

        dealer = new PlayerState(dealerPosition);

        if (firstrun) {
            moneyloader.loadmoney(true, 0, 0, 0);
        } else {
            moneyloader.loadmoney(false, 0, 0, 0);
        }

        switch (playercount) {
            case 1 -> moneyloader.moneyread(players[0], null, null);
            case 2 -> moneyloader.moneyread(players[0], players[1], null);
            case 3 -> moneyloader.moneyread(players[0], players[1], players[2]);
        }

        changebetlabeltext();
    }

    /**
     * Sets up the main game panel with background image.
     */
    private void setupMainPanel() {
        mainpanel = new BackgroundPanel("src/Images/BlackJack/blackjack_table.png");
    }

    /**
     * Loads and registers the pixel font used in UI labels and buttons.
     */
    //chat
    public void setupFont() {
        try {
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf")).deriveFont(80f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets players for a new round and transitions to the hit/stay phase.
     */
    public void resetRound() {
        for (PlayerState player : players) {
            player.hashitstay = false;
            player.hassecondcard = false;
        }
        playertochoose = 0;
        secondround();
    }

    /**
     * Sets up the main game buttons and adds them to the main panel.
     */
    public void SetupButtons() {
        betlabel = buttonSetup.setupbetlabel(pixel, mainpanel);
        addbet = buttonSetup.setupaddbutton(pixel, mainpanel, this);
        lessbet = buttonSetup.setuplessbutton(pixel, mainpanel, this);
        showinfoLabel = buttonSetup.setupinfolabel(pixel, mainpanel);
        showinfoLabel.setText("Player 1 is betting");
        betbutton = buttonSetup.setupbetbutton(pixel, mainpanel, this);
        mainpanel.add(betbutton);
        hitbutton = buttonSetup.setuphitbuttonek(pixel, mainpanel, this);
        mainpanel.add(hitbutton);
        staybutton = buttonSetup.setupstaybuttonek(pixel, mainpanel, this);
        mainpanel.add(staybutton);
        continuebutton = buttonSetup.setupcontinuebuttonek(pixel, mainpanel, this);
        exitbutton = buttonSetup.setupexitbutton(pixel, this);
        mainpanel.add(continuebutton);
        mainpanel.add(exitbutton);
    }

    /**
     * Starts the second round of the game where players can hit or stay.
     */
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

    /**
     * Simulates dealer's behavior based on value and probability.
     */
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

    /**
     * Updates hit/stay button ability based on current player's state.
     */
    //chat
    public void updateability() {
        boolean isDone = players[playertochoose].stay || players[playertochoose].value >= 21;
        players[playertochoose].playerisdone = isDone;
        hitbutton.setEnabled(!isDone);
    }

    /**
     * Updates the bet label to reflect current player's bet.
     */
    public void changebetlabeltext() {
        try {
            if (currentPlayerToBet < totalPlayers) {
                int currentbet = players[currentPlayerToBet].bet;
                betlabel.setText(String.valueOf(currentbet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the info label text and optionally updates the bet label.
     *
     * @param text The new info text to display
     */
    public void changetext(String text) {
        if (currentPlayerToBet < totalPlayers) {
            betlabel.setText(String.valueOf(players[currentPlayerToBet].value));
        }
        showinfoLabel.setText(text);
        mainpanel.repaint();
    }

    /**
     * Launches the BlackJack game.
     *
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        BlackJack blackjack = new BlackJack(true, "seygex");
    }
}
