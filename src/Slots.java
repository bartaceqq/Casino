import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slots extends JFrame {
    JPanel mainPanel = new JPanel();
    SlotMachine slotMachine = new SlotMachine();
    SlotMachine slotMachine2 = new SlotMachine();
    SlotMachine slotMachine3 = new SlotMachine();
    public Slots() {
        this.setTitle("Slots");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1200, 1000);

        setmainpanel();
        this.setVisible(true);
        this.repaint();
    }
    public void setmainpanel(){
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBounds(150, 350, 900,300);
        mainPanel.setLayout(new GridLayout(1,3));
        mainPanel.add(slotMachine);
        mainPanel.add(slotMachine2);
        mainPanel.add(slotMachine3);
        this.add(mainPanel);
        setupSpinButton();
    }
    public void setupSpinButton(){
        JButton spinButton = new JButton("Spin");
        spinButton.setBackground(Color.GRAY);
        spinButton.setForeground(Color.YELLOW);
        spinButton.setBounds(150, 650, 900, 200);
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotMachine.Spin();
                slotMachine2.Spin();
                slotMachine3.Spin();
                System.out.println("seygex");
            }
        });
        this.add(spinButton);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        Slots s = new Slots();

        });
    }
}
