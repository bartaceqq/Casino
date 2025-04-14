import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Roulette extends JFrame {
    private BufferedImage image;
    private BufferedImage arrow;
    private double angle = 0;
    private Random rd = new Random();
    private Timer timer;
    public Boolean betted = false;
    public int bettednum = 0;
    public HashMap<Integer, Integer> bets = new HashMap<>();
    private JLabel winnerLabel = new JLabel("W I N N E R: ");
    private JButton spinButton = new JButton("Spin");
    private int[] numbers = new int[37];
    private JPanel panel = new JPanel();
    public JPanel betpanel; // Updated betpanel declaration
    public int bet = 100;
    JLabel betlabel = new JLabel(String.valueOf(bet));
    public int money = 1000;
    JLabel moneylabel = new JLabel("money: " + money);

    Components components;
    public HashMap<JButton, Integer> buttons = new HashMap<>();

    public Roulette(Components components) {
        this.components = components;
        components.addnumbers(numbers);

        winnerLabel.setFont(new Font("SansSerif", Font.PLAIN, 36));
        this.setTitle("Roulette");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 1000);
        this.setLayout(null);

        try {
            image = ImageIO.read(new File("src/Images/Roulette/roulette_real.png"));
            arrow = ImageIO.read(new File("src/Images/Roulette/Arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        betpanel = new JPanel();
        betpanel.setLayout(null);
        betpanel.setBounds(500, 100, 200, 100);// Position + size
        panel.add(betpanel); // Add it to panel so it appears on top
        setupmoneylabel();
        spinButton.setBounds(650, 700, 200, 100);
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(spinButton);
        panel.add(winnerLabel);
        winnerLabel.setBounds(900, 700, 400, 100);
        betpanel.setVisible(false);
        components.addbuttons(panel, buttons, Roulette.this);
        timer = new Timer(10, e -> {
            angle += 9.7;
            int counter = (int) (angle / 9.7) % 37;
            if (angle >= 720) {
                int count = rd.nextInt(10);
                if (count == 3) {
                    timer.stop();
                    System.out.println("number: " + numbers[counter]);
                    rewritelabel(numbers[counter]);
                }
            }
            panel.repaint();
        });

        spinButton.addActionListener(e -> {
            angle = 0;
            timer.start();
        });

        this.setVisible(true);
        setupbetpanel();
    }

    public void rewritelabel(int counter) {
        winnerLabel.setText("W I N N E R: " + counter);
        if (bets.containsKey(counter)) {
            int sum = bets.get(counter);
            money += sum * 2;
            moneylabel.setText("money: " + money);
        }
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
        addbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                bet += 100;
                betlabel.setText(String.valueOf(bet));
                betlabel.setText(String.valueOf(bet));
                System.out.println("proslo " + bet);
            }
        });
        bett.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                betted = true;
                bettednum = bet;
                money -= bet;
                bet = 0;
                betlabel.setText(String.valueOf(bet));
                betpanel.setVisible(false);
                panel.repaint();
                moneylabel.setText("money: " + money);
            }
        });
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
