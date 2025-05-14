import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SecHovering implements MouseListener {
    int chooser;
    JLabel label;
    public SecHovering(int chooser, JLabel betlabel) {
        this.chooser = chooser;
        this.label = betlabel;
    }

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
        JButton  button = (JButton)e.getSource();
        ImageIcon image = null;
        if (chooser == 1) {
            image = new ImageIcon("src/Images/Slots/plusrefactor.png");
        } else {
            image = new ImageIcon("src/Images/Slots/minusrefactor.png");
        }
        if (image != null) {
            button.setIcon(image);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton  button = (JButton)e.getSource();
        ImageIcon image = null;
        if (chooser == 1) {
            image = new ImageIcon("src/Images/Slots/plus.png");
        } else {
            image = new ImageIcon("src/Images/Slots/minus.png");
        }
        if (image != null) {
            button.setIcon(image);
        }
    }
}
