import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class SlotMachine extends JPanel {
    private JLabel slotLabel;
    CheckIfMatch checkIfMatch;
    private Timer timer;
    public int randomer = 10;
    private ArrayList<ImageIcon> symbols;
    public int thenumber;
    // 👇 Move these OUTSIDE so they persist
    private int count = 0;
    public boolean spinning = false;
    private int fruitcount = 0;
    private Slots slots;
    private String name;
    int col;
    int row;



    public void runtimer(){

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
                            thenumber =fruitcount;
                            checkIfMatch.addtocounter(SlotMachine.this);
                            count = 0;
                            spinning = false;
                            randomer = 50;
                    }
                }
                slotLabel.setIcon(symbols.get(fruitcount));
                slots.repaint();
                fruitcount = (fruitcount + 1) % symbols.size();
            }

        });

        timer.restart();
    }
    public SlotMachine(Slots slots,String name, int row, int col, CheckIfMatch checkIfMatch) {
       this.row = row;
       this.col = col;
       this.checkIfMatch = checkIfMatch;

        this.name=name;
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
        symbols.add(new ImageIcon("src/Images/Fruit/Banana.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/WaterM.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/grape.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/lemon.png"));
        symbols.add(new ImageIcon("src/Images/Fruit/cherry.png"));

    }
    public void Spin(){
        if (!spinning) {
            spinning = true; // set early to avoid double calls
            runtimer();
        }
    }

    public void reset(){
        ;
    }
}
