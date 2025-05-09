import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Hovering implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton)e.getSource();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButtonClicked.png");
        button.setIcon(spinicon);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton)e.getSource();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        button.setIcon(spinicon);
    }
}
