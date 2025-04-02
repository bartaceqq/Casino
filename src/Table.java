import javax.swing.*;
import java.awt.*;

public class Table extends JFrame {
   JPanel blackjackpanel = new BlackJack();
   JPanel Pokerpanel = new BlackJack();
   JPanel slots = new Slots();
   JPanel roulette = new Roulette();
    public Table() {
        this.setTitle("Table");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);

        this.setLayout(null);
        this.add(blackjackpanel);
        blackjackpanel.setBounds(0, 0, 500, 500); // Example size
        this.setVisible(true);
    }

public static void main(String[] args) {
        new Table();
}
}

