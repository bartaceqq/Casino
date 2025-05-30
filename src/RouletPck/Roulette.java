package RouletPck;

import MoneyLoaders.MainMoneyLoader;
import MainMenicko.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * This class represents a Roulette game implemented with a graphical user interface using Swing.
 * It supports placing bets on numbers and colors, spinning the roulette wheel, and handling
 * the game logic for winnings and losses.
 */
public class Roulette extends JFrame {
    /** Map to keep track of which numbers are black (true) or red (false) */
    public HashMap<Integer, Boolean> isblack = new HashMap<>();

    /** Button to exit the roulette game */
    JButton exitbutton;

    /** Amount bet on red */
    public int redbet = 0;

    /** Flag for choosing color betting mode */
    public boolean colorchoose = false;

    /** Flag indicating if the chosen color is black */
    public boolean isblackie = false;

    /** Amount bet on black */
    public int blackbet = 0;

    /** Roulette wheel image */
    private BufferedImage image;

    /** Arrow image indicating the winning slot */
    private BufferedImage arrow;

    /** Current rotation angle of the roulette wheel */
    private double angle = 0;

    /** Button to spin the roulette wheel */
    private JButton spinButton = new JButton("Spin");

    /** Array representing roulette numbers from 0 to 36 */
    private int[] numbers = new int[37];

    /** Random generator for spin results */
    private Random rd = new Random();

    /** Timer to animate the spinning wheel */
    private Timer timer;

    /** Main panel where roulette and controls are drawn */
    private JPanel panel = new JPanel();

    /** Selected number to place a bet on */
    public int selectedNumber = -1;

    /** Map storing bets placed on numbers */
    public HashMap<Integer, Integer> bets = new HashMap<>();

    /** Panel showing bet controls */
    public JPanel betpanel;

    /** Current bet amount */
    public int bet = 0;

    /** Label showing the current bet */
    JLabel betlabel = new JLabel(String.valueOf(bet));

    /** Custom pixel font for UI */
    Font pixelFont;

    /** Player's current money */
    public int money = 1000;

    /** Label displaying player's money */
    JLabel moneylabel = new JLabel("money: " + money);

    /** Components helper class instance for UI elements and game logic */
    Components components;

    /** Map from buttons to their corresponding roulette numbers */
    public HashMap<JButton, Integer> buttons = new HashMap<>();

    /**
     * Constructs a Roulette game JFrame with necessary UI, event listeners, and data initialization.
     *
     * @param components The Components instance used for adding buttons and managing game logic.
     */
    public Roulette(Components components) {
        MainMoneyLoader mainMoneyLoader = new MainMoneyLoader();
        money = mainMoneyLoader.loadMoney();
        moneylabel.setText("money: " + money);

        try {
            // Load custom pixel font
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf"))
                    .deriveFont(80f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.components = components;
        initializeData();
        initializeUI();
        setupListeners();
        setupexitbutton();

        // Disable the default close operation (disable X button)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Sets up listeners for buttons and timer.
     */
    private void setupListeners() {
        spinButton.addActionListener(e -> {
            angle = 0;
            timer.start();
        });

        setuptimer();
    }

    /**
     * Loads roulette numbers, colors, and images.
     */
    private void initializeData() {
        components.addnumbers(numbers);
        components.setupblackredmap(isblack);
        try {
            image = ImageIO.read(new File("src/Images/Roulette/roulette_real.png"));
            arrow = ImageIO.read(new File("src/Images/Roulette/Arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the UI components including main panel, bet panel, buttons, and labels.
     */
    private void initializeUI() {
        this.setTitle("RouletPck");
        this.setSize(1200, 1000);
        this.setLayout(null);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image == null) return;
                Graphics2D g2d = (Graphics2D) g;
                int x = (getWidth() / 3) * 2;
                int y = (getHeight() / 2);
                int imgX = -image.getWidth() / 2;
                int imgY = -image.getHeight() / 2;
                AffineTransform at = new AffineTransform();
                at.translate(x, y);
                at.rotate(Math.toRadians(angle));
                at.translate(imgX, imgY);
                g2d.drawImage(image, at, null);
                g2d.drawImage(arrow, 750, 200, 100, 200, null);
            }
        };
        panel.setBounds(0, 0, 1200, 1000);
        panel.setBackground(Color.decode("#008000"));
        panel.setLayout(null);
        this.add(panel);

        // Bet panel setup
        betpanel = new JPanel();
        betpanel.setLayout(null);
        betpanel.setBounds(500, 100, 200, 100);
        panel.add(betpanel);
        betpanel.setVisible(false);

        // Spin button setup
        spinButton.setBounds(650, 700, 200, 100);
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(spinButton);

        setupmoneylabel();
        setupbetpanel();
        components.addbuttons(panel, buttons, this);
    }

    /**
     * Sets up the spinning animation timer and handles the roulette spin logic.
     */
    public void setuptimer() {
        timer = new Timer(10, e -> {
            angle += 9.7;
            int counter = (int) (angle / 9.7) % 37;
            if (angle >= 720) {
                int count = rd.nextInt(10);
                if (count == 3) {
                    timer.stop();

                    // Output winning number for debug
                    System.out.println("number: " + numbers[counter]);
                    System.out.println("redbet test1: " + redbet);

                    // Calculate money based on black/red bets
                    money = components.blackandredmoneymaker(blackbet, redbet, isblack, numbers[counter], money, panel, Roulette.this);
                    System.out.println(money + " <- penizky");
                    moneylabel.setText("money: " + money);

                    // Check if player bet on the winning number
                    if (bets.containsKey(numbers[counter])) {
                        money += bets.get(numbers[counter]) * 10;  // 10x payout
                        moneylabel.setText("money: " + money);
                    }

                    // Reset bets
                    redbet = 0;
                    blackbet = 0;
                    bets.clear();
                }
            }
            panel.repaint();
        });
    }

    /**
     * Adds listener to "+" button to increase the bet amount.
     * @param addbutton The button to add the listener to.
     */
    public void addbuttonListener(JButton addbutton) {
        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bet += 100;
                betlabel.setText(String.valueOf(bet));
                System.out.println("proslo " + bet);
            }
        });
    }

    /**
     * Adds listener to "BET" button to place the bet on the selected number or color.
     * @param bett The bet button to attach the listener to.
     */
    public void bettbuttonListener(JButton bett) {
        bett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!colorchoose) {
                    if (selectedNumber != -1 && bet > 0) {
                        money -= bet;
                        bets.put(selectedNumber, bet);
                        System.out.println("Storing bet: Number = " + selectedNumber + ", Bet = " + bet);
                        moneylabel.setText("money: " + money);

                        selectedNumber = -1;
                        bet = 0;
                        betlabel.setText("0");
                        betpanel.setVisible(false);

                        System.out.println("Current bets:");
                        bets.forEach((num, b) -> System.out.println("Number: " + num + " | Bet: " + b));
                    }
                } else {
                    System.out.println("proslo vybyrani berev");
                    if (isblackie) {
                        System.out.println("prosla cerna");
                        blackbet = bet;
                        System.out.println(blackbet + " black bet");
                    } else {
                        System.out.println("prosla cervena");
                        redbet = bet;
                        System.out.println(redbet + " red bet");
                    }
                    money -= bet;
                    bet = 0;
                    betlabel.setText("0");
                    moneylabel.setText("money: " + money);

                    betpanel.setVisible(false);
                    panel.repaint();
                    colorchoose = false;
                }
            }
        });
    }

    /**
     * Adds listener to "-" button to decrease the bet amount.
     * @param lessbutton The button to add the listener to.
     */
    public void lessbuttonListener(JButton lessbutton) {
        lessbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bet > 100) {
                    bet -= 100;
                    betlabel.setText(String.valueOf(bet));
                    System.out.println("proslo " + bet);
                }
            }
        });
    }

    /**
     * Sets up the bet panel UI with buttons and labels.
     */
    public void setupbetpanel() {
        JButton addbutton = new JButton("+");
        addbutton.setFocusable(false);
        JButton lessbutton = new JButton("-");
        lessbutton.setFocusable(false);
        JButton bett = new JButton("BET");
        bett.setFocusable(false);

        Font font = new Font("SansSerif", Font.BOLD, 30);
        betlabel.setFont(font);

        betpanel.setLayout(new GridLayout(2, 1));
        JPanel numberpanel = new JPanel();
        numberpanel.add(betlabel);

        JPanel buttonspanel = new JPanel();
        buttonspanel.add(addbutton);
        buttonspanel.add(lessbutton);
        buttonspanel.add(bett);

        addbuttonListener(addbutton);
        bettbuttonListener(bett);
        lessbuttonListener(lessbutton);

        betpanel.add(numberpanel, CENTER_ALIGNMENT);
        betpanel.add(buttonspanel);

        this.repaint();
    }

    /**
     * Sets up the label showing player's money.
     */
    public void setupmoneylabel() {
        JPanel moneypanel = new JPanel();
        moneypanel.setBounds(800, 100, 300, 100);
        moneypanel.setOpaque(false);
        panel.add(moneypanel);

        moneylabel.setFont(new Font("Arial", Font.BOLD, 40));
        moneypanel.add(moneylabel);
    }

    /**
     * Sets up the exit button allowing the user to leave the roulette game,
     * saving money and returning to the main menu.
     */
    public void setupexitbutton() {
        exitbutton = new JButton("Exit");
        exitbutton.setFont(pixelFont);
        exitbutton.setBounds(1050, 0, 150, 100);
        exitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMoneyLoader mainMoneyLoader = new MainMoneyLoader();
                mainMoneyLoader.savemoney(money);
                MainMenu mainmenu = new MainMenu();
                Roulette.this.dispose();
            }
        });
        panel.add(exitbutton);
    }

    /**
     * Main method to launch the Roulette game.
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Components components1 = new Components();
        new Roulette(components1);
    }
}
