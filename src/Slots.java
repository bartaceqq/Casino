import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;

public class Slots extends JFrame {
    HashMap<SlotMachine, Integer> slotlist = new HashMap<>();
    private JButton spinbutton;
    public boolean drawline = false;
    JPanel mainpanel;
    int money = 1000;
    public int payout = 0;
    public int bet = 0;
    CheckIfMatch checkifmatch = new CheckIfMatch(Slots.this);
    ArrayList<Point[]> pointlist = new ArrayList<>();
    JButton plusbet = new JButton();
    JButton minusbet = new JButton();
    JLabel betlabel = new JLabel();
    JLabel moneylabel = new JLabel();
    Font pixelFont;

    public Slots() {
        try {


            //chat
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Images/Slots/Jersey10-Regular.ttf"))
                    .deriveFont(80f); // Set the font size (can be smaller/larger as needed)

            // Register the font
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
            //konec chata
        } catch (Exception e) {
            e.printStackTrace();
        }
        setUpMainThings();
        addSlots();
        spinButttonSetup();
        setupbetpart();
    }

    public void setUpMainThings() {
        this.setTitle("Slots");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setResizable(false);

        mainpanel = new BackgroundPanel("src/Images/Slots/Background.png");
        mainpanel.setLayout(null);
        this.add(mainpanel);

        this.setVisible(true);
    }

    public void addmoney() {
        money += payout;
        moneylabel.setText("$" + money);
    }

    public void addSlots() {
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
    }

    public void addpoints(ArrayList<Point[]> points) {
        pointlist.clear();
        pointlist.addAll(points);
    }

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

    public void setupmoneylabel() {
        moneylabel.setFont(pixelFont);
        moneylabel.setText("$" + money);
        moneylabel.setForeground(Color.WHITE);
        moneylabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneylabel.setVerticalAlignment(SwingConstants.CENTER);
        moneylabel.setBounds(600, -50, 400, 200);
    }

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
                if (can == true) {
                    drawline = false;
                    payout = 0;
                    money -= bet;
                    moneylabel.setText("$" + money);
                    for (SlotMachine slot : slotlist.keySet()) {

                        slot.Spin();
                    }
                }
            }
        });
        spinbutton.addMouseListener(new Hovering());

    }

    public void setupplus() {
        plusbet.setBounds(520, 30, 100, 50);
        ImageIcon plusbeticon = new ImageIcon("src/Images/Slots/plus.png");
        plusbet.setIcon(plusbeticon);
        plusbet.setContentAreaFilled(false);
        plusbet.setBorderPainted(false);
        plusbet.setFocusPainted(false);
        /*
        plusbet.addMouseListener(new SecHovering(1, betlabel));
        */

        plusbet.addActionListener(new BetHandle(1, betlabel, Slots.this));
    }

    public void setupminus() {
        minusbet.setBounds(180, 30, 100, 50);
        ImageIcon minusbeticon = new ImageIcon("src/Images/Slots/minus.png");
        minusbet.setIcon(minusbeticon);
        minusbet.setContentAreaFilled(false);
        minusbet.setBorderPainted(false);
        minusbet.setFocusPainted(false);
        /*
        minusbet.addMouseListener(new SecHovering(2, betlabel));
        */

        minusbet.addActionListener(new BetHandle(2, betlabel, Slots.this));
    }

    public void setupbetlabel() {
        betlabel.setFont(pixelFont);
        betlabel.setText("0");
        betlabel.setForeground(Color.WHITE);
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setBounds(210, -50, 400, 200);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Slots::new);
    }
}
