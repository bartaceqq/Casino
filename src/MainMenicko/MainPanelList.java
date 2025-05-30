package MainMenicko;

import BlackJackPck.BlackJack;
import RouletPck.Components;
import RouletPck.Roulette;
import SlotsPck.Slots;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A MouseAdapter that listens for mouse events on the main menu panel.
 * <p>
 * Depending on the location of the mouse click, it launches different game windows
 * (Slots, Roulette, BlackJack) and disposes of the main menu.
 * It also logs mouse events to the console.
 * </p>
 */
public class MainPanelList extends MouseAdapter {
    private final MainMenu mainMenu;

    /**
     * Constructs a MainPanelList that handles mouse events for the given MainMenu.
     *
     * @param mainMenu the main menu window to be controlled by this listener.
     */
    public MainPanelList(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Handles mouse click events. Depending on the click position,
     * launches the corresponding game and closes the main menu.
     *
     * @param e the MouseEvent containing details of the click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: " + e.getPoint());
        Point p = e.getPoint();
        if (p.y < 490 && p.x < 850) {
            Slots slots = new Slots();
            mainMenu.dispose();
        }
        if (p.y > 490 && p.x < 425) {
            Components components = new Components();
            Roulette roulette = new Roulette(components);
            mainMenu.dispose();
        }
        if (p.y > 490 && p.x > 425 && p.x < 785) {
            BlackJack blackJack = new BlackJack(true, "Black Jack");
            mainMenu.dispose();
        }
        if (p.y > 490 && p.x > 785) {
            Components components = new Components();
            Roulette roulette = new Roulette(components);
            mainMenu.dispose();
        }
    }

    /**
     * Handles mouse press events.
     *
     * @param e the MouseEvent describing the press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
    }

    /**
     * Handles mouse release events.
     *
     * @param e the MouseEvent describing the release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
    }

    /**
     * Handles mouse entering the component.
     *
     * @param e the MouseEvent describing the mouse entering.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered component");
    }

    /**
     * Handles mouse exiting the component.
     *
     * @param e the MouseEvent describing the mouse exiting.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited component");
    }

    /**
     * Handles mouse drag events.
     *
     * @param e the MouseEvent describing the drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged to: " + e.getPoint());
    }

    /**
     * Handles mouse move events.
     *
     * @param e the MouseEvent describing the movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse moved to: " + e.getPoint());
    }
}
