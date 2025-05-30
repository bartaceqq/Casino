package PopUp;

import BlackJackPck.BlackJack;

import javax.swing.*;
import java.awt.*;

/**
 * PopUp dialog for selecting the number of players in the Blackjack game.
 *
 * This modal dialog blocks input to the parent JFrame until a selection is made.
 * It presents three buttons (1, 2, or 3 players) and updates the BlackJack game
 * accordingly when a selection is made.
 */
public class PopUp extends JDialog {

    /**
     * Constructs the PopUp dialog.
     *
     * @param parent    The parent JFrame over which this dialog is displayed.
     * @param text      The message to display in the dialog.
     * @param blackJack The instance of the BlackJack game to configure based on user input.
     */
    public PopUp(JFrame parent, String text, BlackJack blackJack) {
        super(parent, "POP UP", true); // Modal dialog

        // Create and add the message label, centered
        JLabel messageLabel = new JLabel(text, SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        // Create a panel to hold player selection buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Add buttons for selecting 1 to 3 players
        for (int i = 1; i <= 3; i++) {
            int playerCount = i;
            JButton button = new JButton(String.valueOf(playerCount));

            // When a button is clicked, set player count in BlackJack and initialize positions
            button.addActionListener(e -> {
                blackJack.playercount = playerCount;
                blackJack.setuppositions();
                dispose(); // Close the dialog
            });

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true); // Show dialog immediately
    }
}
