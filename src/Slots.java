import Images.Slots.SlotsComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Slots extends JFrame {
    HashMap<SlotMachine, Integer> slotlist = new HashMap<>();
    private JButton spinbutton;
    JPanel mainpanel;
    CheckIfMatch checkifmatch = new CheckIfMatch();

    public Slots() {
        setUpMainThings();
        addSlots();
        spinButttonSetup();
    }

    public void setUpMainThings() {
        this.setTitle("Slots");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setResizable(false);
        this.setVisible(true);

        mainpanel = new BackgroundPanel("src/Images/Slots/Background.png");
        mainpanel.setLayout(null);
        this.add(mainpanel);
    }

    public void addSlots() {
        int startX = 160;
        int width = 200;
        int height = 300;
        int spacing = 170; // Horizontal space between slots
        int slotsPerRow = 5;
        int baseY = 350;
        int rowOffset = 150;

        for (int i = 1; i <= 15; i++) {
            int row = (i - 1) / slotsPerRow;
            int col = (i - 1) % slotsPerRow;
            int x = startX + spacing * col;
            int y = baseY - row * rowOffset;

            SlotMachine slot = new SlotMachine(this, String.valueOf(i), row, col, checkifmatch);
            slot.setBounds(x, y, width, height);
            slotlist.put(slot, i);
            mainpanel.add(slot);
        }
    }

    public void spinButttonSetup() {
        spinbutton = new JButton();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        spinbutton.setIcon(spinicon);
        spinbutton.setContentAreaFilled(false);
        spinbutton.setBorderPainted(false);
        spinbutton.setFocusPainted(false);
        spinbutton.setBounds(150, 600, 900, 200);
        mainpanel.add(spinbutton);
        setupspinbuttonfunction();
    }

    public void setupspinbuttonfunction() {
        spinbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SlotMachine slot : slotlist.keySet()) {
                    slot.Spin();
                }
            }
        });
        spinbutton.addMouseListener(new Hovering());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Slots());
    }
}
