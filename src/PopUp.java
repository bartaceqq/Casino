import javax.swing.*;
import java.awt.*;

public class PopUp extends JDialog {

    public PopUp(JFrame parent, String text, BlackJack blackJack) {
        super(parent, "POP UP", true);

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

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
