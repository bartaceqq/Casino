package SlotsPck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

/**
 * SlotMachine represents a single reel or slot in a slot machine game.
 * It displays a sequence of symbols that spin and stop at a random symbol.
 * Handles the animation of spinning symbols and notifies a listener to check for matches.
 */
public class SlotMachine extends JPanel {
    private JLabel slotLabel;
    private CheckIfMatch checkIfMatch;
    private Timer timer;
    private HashMap<Integer, ImageIcon> symbols;
    public int thenumber;  // Stores the current symbol index shown
    private int timertime = 0;  // Counter for spin duration
    public boolean spinning = false;  // Flag to prevent multiple spins simultaneously
    private Slots slots;  // Reference to parent Slots object (if needed)
    private String name;  // Identifier or name for this slot machine reel
    int col;  // Column position (if arranged in a grid)
    int row;  // Row position (if arranged in a grid)

    /**
     * Constructs a SlotMachine instance.
     *
     * @param slots        Reference to the parent Slots controller object.
     * @param name         A string name or identifier for this slot machine.
     * @param row          The row index where this slot is positioned.
     * @param col          The column index where this slot is positioned.
     * @param checkIfMatch Listener that will handle checking if the slot results match.
     */
    public SlotMachine(Slots slots, String name, int row, int col, CheckIfMatch checkIfMatch) {
        this.row = row;
        this.col = col;
        this.checkIfMatch = checkIfMatch;
        this.name = name;
        this.slots = slots;

        this.setOpaque(false);
        setLayout(new BorderLayout());
        setSize(200, 300);

        // Setup label to display the slot symbol image
        slotLabel = new JLabel();
        slotLabel.setHorizontalAlignment(JLabel.CENTER);
        slotLabel.setVerticalAlignment(JLabel.CENTER);
        slotLabel.setOpaque(false);
        add(slotLabel, BorderLayout.CENTER);

        // Load symbols and associate each with an integer index
        symbols = new HashMap<>();
        symbols.put(0, new ImageIcon("src/Images/Fruit/Banana.png"));
        symbols.put(1, new ImageIcon("src/Images/Fruit/WaterM.png"));
        symbols.put(2, new ImageIcon("src/Images/Fruit/grape.png"));
        symbols.put(3, new ImageIcon("src/Images/Fruit/lemon.png"));
        symbols.put(4, new ImageIcon("src/Images/Fruit/cherry.png"));
    }

    /**
     * Starts spinning the slot if it is not already spinning.
     * This triggers a timer that animates symbol changes.
     */
    public void Spin() {
        if (!spinning) {
            spinning = true;  // Prevent double spins
            runtimer();
        }
    }

    /**
     * Runs the timer to simulate the spinning of the slot.
     * The displayed symbol changes rapidly until the timer stops after ~3 seconds.
     * After stopping, the final symbol is randomly selected.
     * Notifies the checkIfMatch listener to evaluate the outcome.
     */
    public void runtimer() {
        spinning = true;
        timertime = 0;

        timer = new Timer(10, new ActionListener() { // Timer fires every 10ms
            @Override
            public void actionPerformed(ActionEvent e) {
                timertime++;

                Random rand = new Random();

                // Change the slot icon to a random symbol each timer tick to simulate spinning
                int randomIndex = rand.nextInt(symbols.size());
                slotLabel.setIcon(symbols.get(randomIndex));
                thenumber = randomIndex;

                // Stop spinning after ~3 seconds (60 * 10ms)
                if (timertime >= 300) {
                    timer.stop();
                    spinning = false;

                    // Pick a final symbol randomly to show as the stopping result
                    int finalIndex = rand.nextInt(symbols.size());
                    slotLabel.setIcon(symbols.get(finalIndex));
                    thenumber = finalIndex;

                    // Inform listener to check if slot symbols match
                    checkIfMatch.addtocounter(SlotMachine.this);
                }
            }
        });

        timer.start();
    }

    /**
     * Resets the slot machine state.
     * Placeholder for potential future reset logic.
     */
    public void reset() {
        // Optional future logic for resetting slot machine state
    }
}
