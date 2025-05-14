import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Slots extends JFrame {
    HashMap<SlotMachine, Integer> slotlist = new HashMap<>();
    private JButton spinbutton;
    public boolean drawline = false;
    JPanel mainpanel;
    CheckIfMatch checkifmatch = new CheckIfMatch(Slots.this);
    ArrayList<Point[]> pointlist = new ArrayList<>();

    public Slots() {
        setUpMainThings();
        addSlots();
        spinButttonSetup();
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
                drawline = false;
                for (SlotMachine slot : slotlist.keySet()) {
                    slot.Spin();
                }
            }
        });
        spinbutton.addMouseListener(new Hovering());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Slots::new);
    }
}
