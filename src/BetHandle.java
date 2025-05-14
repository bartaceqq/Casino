import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BetHandle implements ActionListener {
    JLabel label;
    int chooser;
    Slots slots;
    public BetHandle(int chooser, JLabel label, Slots slots) {
        this.chooser = chooser;
        this.label = label;
        this.slots = slots;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
        // Get the current bet value
        int currentBet = Integer.parseInt(label.getText());

        switch (chooser) {
            case 1:  // + button logic (increase bet, decrease money)
                if (currentBet < 5000 && slots.bet+50 <= slots.money) {
                    slots.bet+=50;// Check if there's enough money
                    currentBet += 50;  // Increase bet
                      // Decrease money
                    label.setText(String.valueOf(currentBet));  // Update the bet label
                    // Update the money label
                    System.out.println("Bet increased. Current Bet: " + currentBet + ", Money: " + slots.money);
                }
                break;

            case 2:  // - button logic (decrease bet, increase money)
                if (currentBet > 50) {
                    slots.bet-=50;// Check if the bet is greater than the minimum amount
                    currentBet -= 50;  // Decrease bet// Increase money
                    label.setText(String.valueOf(currentBet));  // Update the bet label
                    // Update the money label
                    System.out.println("Bet decreased. Current Bet: " + currentBet + ", Money: " + slots.money);
                }
                break;
        }
    }
}
