import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SlotMachine extends JPanel {
    private JLabel slotLabel;
    CheckIfMatch checkIfMatch;
    private Timer timer;
    private HashMap<Integer, ImageIcon> symbols;
    public int thenumber;
    private int count = 0;
    public boolean spinning = false;
    private int timertime = 0;
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

        symbols = new HashMap<>();
        symbols.put(0, new ImageIcon("src/Images/Fruit/Banana.png"));   // 0
        symbols.put(1, new ImageIcon("src/Images/Fruit/WaterM.png"));   // 1
        symbols.put(2, new ImageIcon("src/Images/Fruit/grape.png"));    // 2
        symbols.put(3, new ImageIcon("src/Images/Fruit/lemon.png"));    // 3
        symbols.put(4, new ImageIcon("src/Images/Fruit/cherry.png"));   // 4
    }

    public void Spin() {
        if (!spinning) {
            spinning = true; // Prevent double spins
            runtimer();
        }
    }

    public void runtimer() {
        spinning = true;
        timertime = 0;

        timer = new Timer(10, new ActionListener() { // Fast smooth spinning every 50ms
            @Override
            public void actionPerformed(ActionEvent e) {
                timertime++;

                // Pick a **random symbol every frame** to simulate real slot spinning
                Random rand = new Random();
                int randomIndex = rand.nextInt(symbols.size());
                slotLabel.setIcon(symbols.get(randomIndex));
                thenumber = randomIndex;

                // Stop after ~3 seconds (adjustable by changing timertime limit)
                if (timertime >= 60) { // 60 iterations × 50ms = 3000ms (3 seconds)
                    timer.stop();
                    spinning = false;

                    // Pick a **final random symbol** instead of just the last iteration
                    int finalIndex = rand.nextInt(symbols.size());
                    slotLabel.setIcon(symbols.get(finalIndex));
                    thenumber = finalIndex;

                    checkIfMatch.addtocounter(SlotMachine.this); // Trigger match checking
                }
            }
        });

        timer.start();
    }


    public void reset() {
        // Optional future logic
    }
}
