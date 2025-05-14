import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SlotMachine extends JPanel {
    private JLabel slotLabel;
    CheckIfMatch checkIfMatch;
    private Timer timer;
    public int randomer = 10;
    private ArrayList<ImageIcon> symbols;
    public int thenumber;
    private int count = 0;
    public boolean spinning = false;
    private int fruitcount = 0;
    private Slots slots;
    private String name;
    int col;
    int row;

    public SlotMachine(Slots slots, String name, int row, int col, CheckIfMatch checkIfMatch) {
        this.row = row;
        this.col = col;
        this.checkIfMatch = checkIfMatch;
        this.name = name;
        this.setOpaque(false);
        this.slots = slots;
        setLayout(new BorderLayout());
        setSize(200, 300);

        slotLabel = new JLabel();
        slotLabel.setHorizontalAlignment(JLabel.CENTER);
        slotLabel.setVerticalAlignment(JLabel.CENTER);
        slotLabel.setOpaque(false);
        add(slotLabel, BorderLayout.CENTER);

        symbols = new ArrayList<>();
        symbols.add(new ImageIcon("src/Images/Fruit/Banana.png"));   // 0
        symbols.add(new ImageIcon("src/Images/Fruit/WaterM.png"));   // 1
        symbols.add(new ImageIcon("src/Images/Fruit/grape.png"));    // 2
        symbols.add(new ImageIcon("src/Images/Fruit/lemon.png"));    // 3
        symbols.add(new ImageIcon("src/Images/Fruit/cherry.png"));   // 4
    }

    public void Spin() {
        if (!spinning) {
            spinning = true; // Prevent double spins
            runtimer();
        }
    }

    public void runtimer() {
        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                spinning = true;
                System.out.println("spinning: " + spinning);

                if (count > 100) {
                    Random rd = new Random();
                    int random = rd.nextInt(randomer);
                    System.out.println(random);

                    if (random == 5) {
                        timer.stop();
                        thenumber = fruitcount; // ✅ CORRECTED: assign before increment
                        checkIfMatch.addtocounter(SlotMachine.this);
                        count = 0;
                        spinning = false;
                        randomer = 50;
                    }
                }

                // ✅ Show image BEFORE increment
                slotLabel.setIcon(symbols.get(fruitcount));
                thenumber = fruitcount; // ✅ Set the number that matches the image
                fruitcount = (fruitcount + 1) % symbols.size();
                slots.repaint();
            }
        });

        timer.restart();
    }

    public void reset() {
        // Optional future logic
    }
}
