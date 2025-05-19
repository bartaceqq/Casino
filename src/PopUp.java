import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PopUp extends JDialog {

    public PopUp(JFrame parent, String text) {
        super(parent, "POP UP", true);  // true makes it modal

        JLabel errorLabel = new JLabel(text, SwingConstants.CENTER);
        JButton close = new JButton("CLOSE");

        close.addActionListener((ActionEvent e) -> dispose());

        // Layout
        setLayout(new BorderLayout());
        add(errorLabel, BorderLayout.CENTER);
        add(close, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);  // Center on parent
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);  // This will block interaction with parent until closed
    }

    // Optional for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();  // Temporary dummy parent
            dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummy.setSize(400, 300);
            dummy.setVisible(true);
            new PopUp(dummy, "Test Popup");
        });
    }
}
