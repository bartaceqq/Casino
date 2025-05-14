import java.awt.*;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class CheckIfMatch {
    private final SlotMachine[][] field = new SlotMachine[4][6];
    private final ArrayList<SlotMachine> slotlist = new ArrayList<>();
    private final Slots slots;
    private final ArrayList<Point[]> allpoints = new ArrayList<>();

    public CheckIfMatch(Slots slots) {
        this.slots = slots;
    }

    public void addtocounter(SlotMachine slotmachine) {
        slotlist.add(slotmachine);
        if (slotlist.size() == 15) {
            addintogrid();
        }
    }

    private void addintogrid() {
        for (SlotMachine slot : slotlist) {
            field[slot.row][slot.col] = slot;
        }
        checkit();
    }

    private void checkit() {
        boolean found = false;

        // Vertical matches (3+)
        for (int col = 0; col < 6; col++) {
            int count = 1;
            for (int row = 1; row < 4; row++) {
                if (field[row][col] != null && field[row - 1][col] != null &&
                        field[row][col].thenumber == field[row - 1][col].thenumber) {
                    count++;
                } else {
                    if (count >= 3) {
                        found = true;
                        drawVerticalMatch(row - 1, col, count);
                    }
                    count = 1;
                }
            }
            if (count >= 3) {
                found = true;
                drawVerticalMatch(3, col, count);
            }
        }

        // Horizontal matches (3+)
        for (int row = 0; row < 3; row++) {
            int count = 1;
            for (int col = 1; col < 6; col++) {
                if (field[row][col] != null && field[row][col - 1] != null &&
                        field[row][col].thenumber == field[row][col - 1].thenumber) {
                    count++;
                } else {
                    if (count >= 3) {
                        found = true;
                        drawHorizontalMatch(row, col - 1, count);
                    }
                    count = 1;
                }
            }
            if (count >= 3) {
                found = true;
                drawHorizontalMatch(row, 5, count);
            }
        }

        // Diagonal ↘
        for (int row = 0; row <= 1; row++) {
            for (int col = 0; col <= 2; col++) {
                ArrayList<SlotMachine> match = new ArrayList<>();
                match.add(field[row][col]);
                int r = row + 1;
                int c = col + 1;
                while (r < 4 && c < 6 && field[r][c] != null && field[r - 1][c - 1] != null &&
                        field[r][c].thenumber == field[r - 1][c - 1].thenumber) {
                    match.add(field[r][c]);
                    r++;
                    c++;
                }
                if (match.size() >= 3) {
                    found = true;
                    drawMatchLine(match.toArray(new SlotMachine[0]));
                }
            }
        }

        // Diagonal ↙
        for (int row = 0; row <= 1; row++) {
            for (int col = 3; col < 6; col++) {
                ArrayList<SlotMachine> match = new ArrayList<>();
                match.add(field[row][col]);
                int r = row + 1;
                int c = col - 1;
                while (r < 4 && c >= 0 && field[r][c] != null && field[r - 1][c + 1] != null &&
                        field[r][c].thenumber == field[r - 1][c + 1].thenumber) {
                    match.add(field[r][c]);
                    r++;
                    c--;
                }
                if (match.size() >= 3) {
                    found = true;
                    drawMatchLine(match.toArray(new SlotMachine[0]));
                }
            }
        }

        if (!found) {
            System.out.println("No matches found.");
        }

        slots.addpoints(allpoints);
        slotlist.clear();
        clearGrid();
        allpoints.clear();
    }

    private void drawVerticalMatch(int endRow, int col, int count) {
        SlotMachine[] match = new SlotMachine[count];
        for (int i = 0; i < count; i++) {
            match[i] = field[endRow - i][col];
        }
        drawMatchLine(match);
    }

    private void drawHorizontalMatch(int row, int endCol, int count) {
        SlotMachine[] match = new SlotMachine[count];
        for (int i = 0; i < count; i++) {
            match[i] = field[row][endCol - i];
        }
        drawMatchLine(match);
    }
//chat
    private void drawMatchLine(SlotMachine... slotsToMatch) {
        ArrayList<Point> points = new ArrayList<>();
        for (SlotMachine s : slotsToMatch) {
            if (s != null) points.add(convertToFrameCenter(s));
        }
        allpoints.add(points.toArray(new Point[6]));
        slots.drawline = true;
        slots.repaint();
    }

    private Point convertToFrameCenter(SlotMachine slot) {
        if (slot != null) {
            return SwingUtilities.convertPoint(slot,new Point(slot.getWidth() / 2, slot.getHeight() / 2),
                    slots);
        }
        return null;
    }
//konec chata
    private void clearGrid() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = null;
            }
        }
    }
}
