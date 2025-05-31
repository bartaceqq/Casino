package RouletPck;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener implementation for handling number button clicks
 * in the Roulette game.
 *
 * When a button is clicked, it updates the Roulette's selected number
 * based on the button pressed and makes the betting panel visible.
 */
public class NumbersListener implements ActionListener {
    private final Roulette roulette;

    /**
     * Constructs a NumbersListener associated with the given Roulette instance.
     *
     * @param roulette The Roulette game instance to interact with.
     */
    public NumbersListener(Roulette roulette) {
        this.roulette = roulette;
    }

    /**
     * Handles the button click event.
     * Retrieves the number associated with the clicked button and
     * sets it as the selected number in the Roulette instance.
     * Then makes the bet panel visible.
     *
     * @param e The ActionEvent triggered by clicking a number button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        int number = roulette.buttons.get(source);
        roulette.selectedNumber = number;
        roulette.betpanel.setVisible(true);
    }
}
