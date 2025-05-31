package SlotsPck;

import BackGroundPanel.BackgroundPanel;
import MainMenicko.MainMenu;
import MoneyLoaders.MainMoneyLoader;
import PopUp.PopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

/**
 * Represents the Slots game JFrame.
 * <p>
 * This class manages the Slots GUI including slot machines, betting controls,
 * money management, and spin functionality. It handles the UI layout,
 * user interactions, and updating the display.
 * </p>
 */
public class Slots extends JFrame {
    /** Map of SlotMachine components to their respective IDs */
    HashMap<SlotMachine, Integer> slotlist = new HashMap<>();
    private JButton spinbutton;
    /** Flag to indicate if payout lines should be drawn */
    public boolean drawline = false;
    JPanel mainpanel;
    /** Player's current money */
    int money = 1000;
    private JButton exitbutton;
    /** Current payout amount */
    public int payout = 0;
    /** Current bet amount */
    public int bet = 0;
    CheckIfMatch checkifmatch = new CheckIfMatch(Slots.this);
    ArrayList<Point[]> pointlist = new ArrayList<>();
    JButton plusbet = new JButton();
    JButton minusbet = new JButton();
    JLabel betlabel = new JLabel();
    JLabel moneylabel = new JLabel();
    Font pixelFont;
    MainMoneyLoader mainMoneyLoader = new MainMoneyLoader();

    /**
     * Constructs the Slots JFrame.
     * Initializes money, fonts, UI components, and event handlers.
     */
    public Slots() {
        money = mainMoneyLoader.loadMoney();
        try {
            // Load custom pixel font
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf"))
                    .deriveFont(80f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Disable the default close operation (X button)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUpMainThings();
        addSlots();
        spinButttonSetup();
        setupbetpart();
        setupexitbutton();
    }

    /**
     * Sets up main JFrame properties and initializes the main panel with background.
     */
    public void setUpMainThings() {
        this.setTitle("SlotsPck.Slots");
        this.setSize(1200, 800);
        this.setResizable(false);

        mainpanel = new BackgroundPanel("src/Images/Slots/Background.png");
        mainpanel.setLayout(null);
        this.add(mainpanel);

        this.setVisible(true);
    }

    /**
     * Adds the payout amount to the player's money and updates the money label.
     */
    public void addmoney() {
        money += payout;
        moneylabel.setText("$" + money);
    }

    /**
     * Adds slot machine components to the main panel arranged in rows and columns.
     * Each slot machine is assigned an ID and positioned accordingly.
     */
    public void addSlots() {
        //chat
        int startX = 160;
        int width = 200;
        int height = 300;
        int spacing = 170;
        int slotsPerRow = 5;
        int baseY = 350;
        int rowOffset = 150;

        for (int i = 1; i <= 15; i++) {
            int row = (i - 1) / slotsPerRow;
            int col = (i - 1) % slotsPerRow;
            int x = startX + spacing * col;
            int y = baseY - row * rowOffset;

            SlotMachine slot = new SlotMachine(this, String.valueOf(i), row, col, checkifmatch);
            slot.setBounds(x, y, width, height);
            slotlist.put(slot, i);
            mainpanel.add(slot);
        }
        //
    }

    /**
     * Sets up the exit button which saves the player's money and returns to the main menu.
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
                Slots.this.dispose();
            }
        });
        mainpanel.add(exitbutton);
    }

    /**
     * Clears the current payout line points and adds new ones for drawing.
     *
     * @param points List of Point arrays representing payout lines.
     */
    public void addpoints(ArrayList<Point[]> points) {
        pointlist.clear();
        pointlist.addAll(points);
    }

    /**
     * Paints the payout lines if drawline is enabled.
     * Draws red lines between consecutive points in the point list.
     *
     * @param g Graphics context.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (drawline) {
            for (Point[] points : pointlist) {
                g2d.setColor(Color.RED);

                for (int i = 0; i < points.length - 1; i++) {
                    if (points[i] != null && points[i + 1] != null) {
                        g2d.setStroke(new BasicStroke(5));
                        g2d.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
                    }
                }
            }
        }
    }

    /**
     * Sets up the label showing the player's current money.
     */
    public void setupmoneylabel() {
        moneylabel.setFont(pixelFont);
        moneylabel.setText("$" + money);
        moneylabel.setForeground(Color.WHITE);
        moneylabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneylabel.setVerticalAlignment(SwingConstants.CENTER);
        moneylabel.setBounds(600, -50, 400, 200);
    }

    /**
     * Sets up the spin button including icon, appearance, and click behavior.
     */
    public void spinButttonSetup() {
        spinbutton = new JButton();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        spinbutton.setIcon(spinicon);
        spinbutton.setContentAreaFilled(false);
        spinbutton.setBorderPainted(false);
        spinbutton.setFocusPainted(false);
        spinbutton.setBounds(150, 600, 900, 200);
        mainpanel.add(spinbutton);
        setupspinbuttonfunction();
    }

    /**
     * Adds an action listener to the spin button to start spinning the slots if conditions are met.
     */
    public void setupspinbuttonfunction() {
        spinbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean can = true;
                for (SlotMachine slot : slotlist.keySet()) {
                    if (slot.spinning == true) {
                        can = false;
                    }
                }
                if (can) {
                    if (bet <= money) {
                        drawline = false;
                        payout = 0;
                        money -= bet;
                        moneylabel.setText("$" + money);
                        for (SlotMachine slot : slotlist.keySet()) {
                            slot.Spin();
                        }
                    } else {
                        new PopUp(Slots.this, "U ARE TRYING TO BET MORE THAN YOU HAVE", null);
                    }
                }
            }
        });
        spinbutton.addMouseListener(new Hovering());
    }

    /**
     * Configures the "+" bet button appearance and behavior.
     */
    public void setupplus() {
        plusbet.setBounds(520, 30, 100, 50);
        ImageIcon plusbeticon = new ImageIcon("src/Images/Slots/plus.png");
        plusbet.setIcon(plusbeticon);
        plusbet.setContentAreaFilled(false);
        plusbet.setBorderPainted(false);
        plusbet.setFocusPainted(false);

        plusbet.addActionListener(new BetHandle(1, betlabel, Slots.this));
    }

    /**
     * Configures the "-" bet button appearance and behavior.
     */
    public void setupminus() {
        minusbet.setBounds(180, 30, 100, 50);
        ImageIcon minusbeticon = new ImageIcon("src/Images/Slots/minus.png");
        minusbet.setIcon(minusbeticon);
        minusbet.setContentAreaFilled(false);
        minusbet.setBorderPainted(false);
        minusbet.setFocusPainted(false);

        minusbet.addActionListener(new BetHandle(2, betlabel, Slots.this));
    }

    /**
     * Sets up the label that displays the current bet amount.
     */
    public void setupbetlabel() {
        betlabel.setFont(pixelFont);
        betlabel.setText("0");
        betlabel.setForeground(Color.WHITE);
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setBounds(210, -50, 400, 200);
    }

    /**
     * Sets up all bet-related controls (plus, minus buttons and bet label).
     */
    public void setupbetpart() {
        setupplus();
        mainpanel.add(plusbet);
        setupminus();
        mainpanel.add(minusbet);
        setupbetlabel();
        mainpanel.add(betlabel);
        setupmoneylabel();
        mainpanel.add(moneylabel);
    }

    /**
     * Main method to launch the Slots game UI.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Slots::new);
    }
}
