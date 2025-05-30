package BlackJackPck;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MouseListener implementation that changes the icon of a JButton
 * when the mouse enters or exits it, to provide a hover effect.
 *
 * The icon changes depend on the value of the chooser:
 *  - chooser == 1: uses "plus" icons
 *  - chooser != 1: uses "minus" icons
 */
public class SecHovering implements MouseListener {
    private final int chooser;
    private final JLabel label;

    /**
     * Constructs a SecHovering listener.
     *
     * @param chooser Determines which icon set to use (1 for plus, others for minus).
     * @param betlabel Optional JLabel, currently unused but stored for potential future use.
     */
    public SecHovering(int chooser, JLabel betlabel) {
        this.chooser = chooser;
        this.label = betlabel;
    }

    /**
     * Not used.
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // No action on click
    }

    /**
     * Not used.
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No action on press
    }

    /**
     * Not used.
     * @param e MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No action on release
    }

    /**
     * When the mouse enters the button area, changes the button icon
     * to the "refactor" version of the plus or minus icon depending on chooser.
     *
     * @param e MouseEvent triggered on mouse entering the component
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        ImageIcon image;
        if (chooser == 1) {
            image = new ImageIcon("src/Images/Slots/plusrefactor.png");
        } else {
            image = new ImageIcon("src/Images/Slots/minusrefactor.png");
        }
        button.setIcon(image);
    }

    /**
     * When the mouse exits the button area, resets the button icon
     * to the default plus or minus icon depending on chooser.
     *
     * @param e MouseEvent triggered on mouse exiting the component
     */
    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        ImageIcon image;
        if (chooser == 1) {
            image = new ImageIcon("src/Images/Slots/plus.png");
        } else {
            image = new ImageIcon("src/Images/Slots/minus.png");
        }
        button.setIcon(image);
    }
}
