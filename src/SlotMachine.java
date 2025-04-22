import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SlotMachine extends JFrame {
    private JLabel slotLabel;
    private Timer timer;
    private ArrayList<ImageIcon> symbols;
    private int index = 0;

    // 👇 Move these OUTSIDE so they persist
    private int count = 0;
    private int fruitcount = 0;

    public SlotMachine() {
        setTitle("Slot Machine Reel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setSize(300, 300);
        slotLabel = new JLabel();
        slotLabel.setHorizontalAlignment(JLabel.CENTER);
        slotLabel.setVerticalAlignment(JLabel.CENTER);
        add(slotLabel, BorderLayout.CENTER);

        symbols = new ArrayList<>();
        symbols.add(new ImageIcon("src/fruit1.png"));
        symbols.add(new ImageIcon("src/fruit2.png"));
        symbols.add(new ImageIcon("src/grape.png"));
        symbols.add(new ImageIcon("src/lemon.png"));
        symbols.add(new ImageIcon("src/cherry.png"));

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                if (count > 400) {
                    timer.stop();
                } else {
                    slotLabel.setIcon(symbols.get(fruitcount));
                    fruitcount = (fruitcount + 1) % symbols.size();
                }
            }
        });

       /*
        spinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count = 0;
                fruitcount = 0;
                timer.restart(); // now works every time!
            }
        });
*/
    }

}
