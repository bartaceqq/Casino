import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Hovering implements MouseListener {
    private String originalImg;
    private String hoverImg;
    private PanelForButtons panel;
    private String selection = "";
    Table table;
    Components components;
    public Hovering(String section, PanelForButtons panel, Table table, Components components) {
        this.panel = panel;
        this.components = components;
        this.table = table;
        this.selection = section;
        chooseRightImg(section);
    }
    private void chooseRightImg(String section) {
        switch (section) {
            case "poker":
                originalImg = "src/Images/OrigoImages/Poker_Button.png";
                hoverImg = "src/Images/HoverImages/Poker_O.png";
                break;
            case "slots":
                originalImg = "src/Images/OrigoImages/Slots_Button.png";
                hoverImg = "src/Images/HoverImages/Slots_O.png";
                break;
            case "blackjack":
                originalImg = "src/Images/OrigoImages/Blackjack_Button.png";
                hoverImg = "src/Images/HoverImages/Blackjack_O.png";
                break;
            case "roulette":
                originalImg = "src/Images/OrigoImages/Roulette_Button.png";
                hoverImg = "src/Images/HoverImages/Roulette_O.png";
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        panel.setImage(hoverImg);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        panel.setImage(originalImg);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (selection) {
            case "poker":
                break;
                case "slots":
                    break;
                    case "blackjack":
                        break;
                        case "roulette":
                        table.dispose();
                        new Roulette(components);
                        break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
