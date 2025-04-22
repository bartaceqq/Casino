import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class SlotMachine extends JPanel {
    private JLabel slotLabel;
    private Timer timer;
    private ArrayList<ImageIcon> symbols;
    private int thenumber;
    // 👇 Move these OUTSIDE so they persist
    private int count = 0;
    private int fruitcount = 0;
    public void runtimer(){

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count++;
                if (count > 200) {
                    Random rd = new Random();
                    int random = rd.nextInt(11);
                    System.out.println(random);
                    if (random == 5) {
                            timer.stop();
                            thenumber =fruitcount;
                    }
                }
                slotLabel.setIcon(symbols.get(fruitcount));
                fruitcount = (fruitcount + 1) % symbols.size();
            }

        });
        timer.restart();
    }
    public SlotMachine() {
        setLayout(new BorderLayout());
        setSize(300, 300);
        slotLabel = new JLabel();
        slotLabel.setHorizontalAlignment(JLabel.CENTER);
        slotLabel.setVerticalAlignment(JLabel.CENTER);
        add(slotLabel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        symbols = new ArrayList<>();
        symbols.add(new ImageIcon("src/Images/Fruit/Banana.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/WaterM.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/grape.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/lemon.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/cherry.png"));

    }
    public void Spin(){
       runtimer();
        System.out.println(thenumber);
    }
}
