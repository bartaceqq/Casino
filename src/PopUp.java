import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PopUp extends JDialog {

    public PopUp(JFrame parent, String text, BlackJack blackJack) {
        super(parent, "POP UP", true);  // true makes it modal

        JLabel errorLabel = new JLabel(text, SwingConstants.CENTER);
        JButton close = new JButton("CLOSE");

        close.addActionListener((ActionEvent e) -> dispose());

        // Layout
        setLayout(new BorderLayout());
        add(errorLabel, BorderLayout.CENTER);


        setSize(300, 150);
        setLocationRelativeTo(parent);  // Center on parent
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        if (blackJack != null) {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

            JButton one = new JButton("1");
            one.addActionListener(e -> {
                blackJack.playercount = 1;
                System.out.println(blackJack.playercount);
                blackJack.setuppositions();
                dispose();  // Close dialog after selection
            });
            buttonPanel.add(one);

            JButton two = new JButton("2");
            two.addActionListener(e -> {
                blackJack.playercount = 2;
                System.out.println(blackJack.playercount);
                blackJack.setuppositions();
                dispose();
            });
            buttonPanel.add(two);

            JButton three = new JButton("3");
            three.addActionListener(e -> {
                blackJack.playercount = 3;
                System.out.println(blackJack.playercount);
                blackJack.setuppositions();
                dispose();
            });
            buttonPanel.add(three);

            add(buttonPanel, BorderLayout.SOUTH);

        } else {
            add(close, BorderLayout.SOUTH);
        }
        setVisible(true);  // This will block interaction with parent until closed
    }

    // Optional for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();  // Temporary dummy parent
            dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummy.setSize(400, 300);
            dummy.setVisible(true);
            new PopUp(dummy, "Test Popup", new BlackJack());
        });
    }
}
