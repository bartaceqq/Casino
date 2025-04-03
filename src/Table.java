import javax.swing.*;
import java.awt.*;

public class Table extends JFrame {
   JPanel blackjack = new PanelForButtons("src/Images/OrigoImages/BlackJack_Button.png",-80 ,0, "blackjack", this);
    JPanel poker = new PanelForButtons("src/Images/OrigoImages/Poker_Button.png",0 ,0, "poker", this);
    JPanel slots = new PanelForButtons("src/Images/OrigoImages/Slots_Button.png",-45 ,-50, "slots", this);
    JPanel roulette = new PanelForButtons("src/Images/OrigoImages/Roulette_Button.png",-50 ,-0, "roulette", this);
    public Table() {
        this.setTitle("Table");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
    this.setResizable(false);

        getContentPane().setBackground(new Color(7,77,35));
        this.setLayout(null);
        this.add(blackjack);
        this.add(poker);
        this.add(slots);
        this.add(roulette);

        blackjack.setBounds(0, 0, 500, 500);
        poker.setBounds(500, 0, 500, 500);
        slots.setBounds(0, 500, 500, 500);
        roulette.setBounds(500, 500, 500, 500);
        // Example size
        this.setVisible(true);
    }

public static void main(String[] args) {
        new Table();
}
}

