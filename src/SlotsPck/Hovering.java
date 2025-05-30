package SlotsPck;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A MouseListener implementation that changes a JButton's icon when hovered over.
 * Specifically designed to update the icon for a "Spin" button in the slots game.
 */
public class Hovering implements MouseListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     * No action is performed here.
     *
     * @param e the MouseEvent triggered by the click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // No action on mouseClicked
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * No action is performed here.
     *
     * @param e the MouseEvent triggered by the press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No action on mousePressed
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * No action is performed here.
     *
     * @param e the MouseEvent triggered by the release
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No action on mouseReleased
    }

    /**
     * Invoked when the mouse enters the bounds of a component.
     * Changes the JButton's icon to a "clicked" version to indicate hover state.
     *
     * @param e the MouseEvent triggered when the mouse enters
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButtonClicked.png");
        button.setIcon(spinicon);
    }

    /**
     * Invoked when the mouse exits the bounds of a component.
     * Changes the JButton's icon back to the default "spin" icon.
     *
     * @param e the MouseEvent triggered when the mouse exits
     */
    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        ImageIcon spinicon = new ImageIcon("src/Images/Slots/SpinButton.png");
        button.setIcon(spinicon);
    }
}
