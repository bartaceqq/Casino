import Images.Slots.SlotsComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Slots extends JFrame {
    private SlotMachine slot1;
    private SlotMachine slot2;
    private SlotMachine slot3;
    private SlotMachine slot4;
    private SlotMachine slot5;
    private JButton spinbutton;
    JPanel mainpanel;
    public Slots() {
        setUpMainThings();
        addSlots();
        spinButttonSetup();
    }
        public void setUpMainThings(){
            this.setTitle("Slots");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(1200, 800);
            this.setResizable(false);
            this.setVisible(true);
            mainpanel = new BackgroundPanel("src/Images/Slots/Background.png");
            mainpanel.setLayout(null);
            this.add(mainpanel);
        }
        public void addSlots(){
        slot1 = new SlotMachine(this, "1");
        slot1.setBounds(160, 200, 200, 300);
        slot2 = new SlotMachine(this, "2");
        slot2.setBounds(330, 200, 200, 300);
        slot3 = new SlotMachine(this, "3");
        slot3.setBounds(500, 200, 200, 300);
        slot4 = new SlotMachine(this, "4");
        slot4.setBounds(670, 200, 200, 300);
        slot5 = new SlotMachine(this, "5");
        slot5.setBounds(840, 200, 200, 300);
        mainpanel.add(slot1);
        mainpanel.add(slot2);
        mainpanel.add(slot3);
        mainpanel.add(slot4);
        mainpanel.add(slot5);
        }
        public void spinButttonSetup(){
        spinbutton = new JButton();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        spinbutton.setIcon(spinicon);
        spinbutton.setContentAreaFilled(false);
        spinbutton.setBorderPainted(false);
        spinbutton.setFocusPainted(false);
        spinbutton.setBounds(150, 500, 900, 200);
        mainpanel.add(spinbutton);
        setupspinbuttonfunction();
        }
        public void setupspinbuttonfunction(){
        spinbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                slot1.Spin();
                slot2.Spin();
                slot3.Spin();
                slot4.Spin();
                slot5.Spin();
            }
        });
        spinbutton.addMouseListener(new Hovering());
        }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Slots();
        });
    }
}