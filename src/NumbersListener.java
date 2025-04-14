import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumbersListener implements ActionListener {
    Roulette roulette;
    public NumbersListener(Roulette roulette) {
        this.roulette = roulette;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       JButton source = (JButton) e.getSource();
       roulette.betpanel.setVisible(true);
       if (roulette.betted){
           int number;
            number = roulette.buttons.get(source);
            roulette.bets.put(number, roulette.bet);
            roulette.betted = false;
            roulette.bet = 0;
       }
        System.out.println(source.getX() + "x");
    }
}
