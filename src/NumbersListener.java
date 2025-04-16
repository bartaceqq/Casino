import javax.swing.*;
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
        int number = roulette.buttons.get(source);
        roulette.selectedNumber = number; // store selected number
        roulette.betpanel.setVisible(true);
    }
}