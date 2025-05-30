package SlotsPck;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles bet adjustment actions for the Slots game.
 * <p>
 * This class listens for button clicks to increase or decrease the current bet amount,
 * ensuring the bet stays within valid bounds and updates the associated labels accordingly.
 * </p>
 */
public class BetHandle implements ActionListener {
    private JLabel label;
    private int chooser;
    private Slots slots;

    /**
     * Constructs a BetHandle instance.
     *
     * @param chooser indicates the type of action:
     *                1 for increasing the bet,
     *                2 for decreasing the bet.
     * @param label   the JLabel displaying the current bet amount.
     * @param slots   the Slots instance containing bet and money information.
     */
    public BetHandle(int chooser, JLabel label, Slots slots) {
        this.chooser = chooser;
        this.label = label;
        this.slots = slots;
    }

    /**
     * Invoked when an action occurs.
     * Handles increasing or decreasing the bet based on the chooser value.
     * Ensures the bet does not exceed the maximum allowed (5000) or go below the minimum (50),
     * and that the player has enough money to increase the bet.
     *
     * @param e the action event triggered by button press.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
        // Get the current bet value from the label
        int currentBet = Integer.parseInt(label.getText());

        switch (chooser) {
            case 1:  // Increase bet (+ button)
                // Increase bet only if it stays under max and player has enough money
                if (currentBet < 5000 && slots.bet + 50 <= slots.money) {
                    slots.bet += 50; // Update Slots bet field
                    currentBet += 50; // Increase current bet
                    label.setText(String.valueOf(currentBet)); // Update label text
                    System.out.println("Bet increased. Current Bet: " + currentBet + ", Money: " + slots.money);
                }
                break;

            case 2:  // Decrease bet (- button)
                // Decrease bet only if above minimum
                if (currentBet > 50) {
                    slots.bet -= 50; // Update Slots bet field
                    currentBet -= 50; // Decrease current bet
                    label.setText(String.valueOf(currentBet)); // Update label text
                    System.out.println("Bet decreased. Current Bet: " + currentBet + ", Money: " + slots.money);
                }
                break;
        }
    }
}
