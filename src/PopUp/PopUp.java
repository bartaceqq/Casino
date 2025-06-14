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
     * @param whatis    Determines dialog content: true for player selection, false for bet warning.
     */
    public PopUp(JFrame parent, String text, BlackJack blackJack, boolean whatis) {
        super(parent, "POP UP", true);
        setLayout(new BorderLayout());

        if (whatis) {
            JLabel messageLabel = new JLabel(text, SwingConstants.CENTER);
            add(messageLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            for (int i = 1; i <= 3; i++) {
                int playerCount = i;
                JButton button = new JButton(String.valueOf(playerCount));
                button.addActionListener(e -> {
                    blackJack.playercount = playerCount;
                    blackJack.setuppositions();
                    dispose();
                });
                buttonPanel.add(button);
            }
            add(buttonPanel, BorderLayout.SOUTH);

        } else {
            JLabel warningLabel = new JLabel("You are trying to bet more than you have", SwingConstants.CENTER);
            add(warningLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> dispose());
            buttonPanel.add(okButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
