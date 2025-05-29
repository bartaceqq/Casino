import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanelList extends MouseAdapter {
    MainMenu mainMenu;
    public MainPanelList(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: " + e.getPoint());
        Point p = e.getPoint();
        if (p.y <490 && p.x < 850){
            Slots slots = new Slots();
            mainMenu.dispose();
        }
        if (p.y >490 && p.x < 425){
            Components components = new Components();
            Roulette roulette = new Roulette(components);
            mainMenu.dispose();
        }
        if (p.y >490 && p.x > 425 && p.x < 785){
            BlackJack blackJack = new BlackJack(true, "Black Jack");
            mainMenu.dispose();
        }
        if (p.y >490 && p.x > 785){
            Components components = new Components();
            Roulette roulette = new Roulette(components);
            mainMenu.dispose();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered component");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited component");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged to: " + e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse moved to: " + e.getPoint());
    }
}
