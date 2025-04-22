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

public class Roulette extends JFrame {
    //things for colors
    public HashMap<Integer, Boolean> isblack = new HashMap<>();
    //
    //choose red things
    public int redbet = 0;
    //
    //choose black things
    public boolean colorchoose = false;
    public boolean isblackie = false;
    public int blackbet = 0;
    //
    //images
    private BufferedImage image;
    private BufferedImage arrow;
    //
    //spinning
    private double angle = 0;
    private JButton spinButton = new JButton("Spin");
    private int[] numbers = new int[37];
    private Random rd = new Random();
    private Timer timer;
    //
    //roulette panel
    private JPanel panel = new JPanel();
    //
    //things for bets
    public int selectedNumber = -1;
    public HashMap<Integer, Integer> bets = new HashMap<>();
    public JPanel betpanel;
    public int bet = 0;
    JLabel betlabel = new JLabel(String.valueOf(bet));
    //
    //money visual and money itself handler
    public int money = 1000;
    JLabel moneylabel = new JLabel("money: " + money);
    //
    //components (second class)
    Components components;
    //
    //buttons list
    public HashMap<JButton, Integer> buttons = new HashMap<>();
    // roulette constructor that handles main things about frame and panel
    public Roulette(Components components) {
        this.components = components;
        initializeData();
        initializeUI();
        setupListeners();
        this.setVisible(true);

    }
    private void setupListeners() {
        spinButton.addActionListener(e -> {
            angle = 0;
            timer.start();
        });

        setuptimer();  // still keeps your Timer logic
    }
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
    private void initializeUI() {
        this.setTitle("Roulette");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Bet panel
        betpanel = new JPanel();
        betpanel.setLayout(null);
        betpanel.setBounds(500, 100, 200, 100);
        panel.add(betpanel);
        betpanel.setVisible(false);

        // Spin button
        spinButton.setBounds(650, 700, 200, 100);
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(spinButton);

        // Money label
        setupmoneylabel();

        // Bet panel content
        setupbetpanel();

        // Add buttons for number selection
        components.addbuttons(panel, buttons, this);
    }
    public void setuptimer(){
        timer = new Timer(10, e -> {
            angle += 9.7;
            int counter = (int) (angle / 9.7) % 37;
            if (angle >= 720) {
                int count = rd.nextInt(10);
                if (count == 3) {
                    timer.stop();
                    //end of what was made by chat

                    // Choose winning number
                    System.out.println("number: " + numbers[counter]);
                    System.out.println("redbet test1: " + redbet);
                    money = components.blackandredmoneymaker(blackbet, redbet, isblack, numbers[counter], money, panel, Roulette.this);
                    System.out.println(money + " <- penizky");
                    moneylabel.setText("money: " + money);

                    // In your Timer action listener
                    if (bets.containsKey(numbers[counter])) {
                        // Add the winnings for the winning number
                        money += bets.get(numbers[counter]) * 10;  // 10x payout for this bet
                        moneylabel.setText("money: " + money);
                    }
                    bets.clear();

                }
            }
            panel.repaint();
        });
    }
    public void addbuttonListener(JButton addbutton){
        addbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                bet += 100;
                betlabel.setText(String.valueOf(bet));
                betlabel.setText(String.valueOf(bet));
                System.out.println("proslo " + bet);
            }
        });
    }
    public void bettbuttonListener(JButton bett){
        bett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!colorchoose) {
                    if (selectedNumber != -1 && bet > 0) {
                        // Deduct the money first
                        money -= bet;  // Subtract bet from money

                        // Store the bet
                        bets.put(selectedNumber, bet);
                        System.out.println("Storing bet: Number = " + selectedNumber + ", Bet = " + bet);

                        moneylabel.setText("money: " + money);

                        selectedNumber = -1; // reset the selected number
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

                    // Deduct the money for the color bet
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
    public void lessbuttonListener(JButton lessbutton){
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
    public void setupbetpanel() {
        JButton addbutton = new JButton("+");
        addbutton.setFocusable(false);
        JButton lessbutton = new JButton("-");
        lessbutton.setFocusable(false);
        JButton bett = new JButton("BET");
        lessbutton.setFocusable(false);
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
    public void setupmoneylabel() {
        JPanel moneypanel = new JPanel();
        moneypanel.setBounds(800, 100, 300, 100);
        moneypanel.setOpaque(false);
        panel.add(moneypanel);

        moneylabel.setFont(new Font("Arial", Font.BOLD, 40));
        moneypanel.add(moneylabel);
    }
    public static void main(String[] args) {
        Components components1 = new Components();
        new Roulette(components1);
    }
}
