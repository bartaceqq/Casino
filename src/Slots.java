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
            mainpanel = new JPanel();
            this.add(mainpanel);
        }
        public void addSlots(){
        slot1 = new SlotMachine(this, "1");
        slot2 = new SlotMachine(this, "2");
        slot3 = new SlotMachine(this, "3");
        mainpanel.add(slot1);
        mainpanel.add(slot2);
        mainpanel.add(slot3);
        }
        public void spinButttonSetup(){
        spinbutton = new JButton();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        spinbutton.setIcon(spinicon);
        spinbutton.setContentAreaFilled(false);
        spinbutton.setBorderPainted(false);
        spinbutton.setFocusPainted(false);
        mainpanel.add(spinbutton);
        }
        public void setupspinbuttonfunction(){
        spinbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                slot1.Spin();
                slot2.Spin();
                slot3.Spin();
            }
        });
        }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Slots();
        });
    }
}