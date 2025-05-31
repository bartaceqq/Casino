package SlotsPck;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 * Class responsible for checking matching patterns in a slot machine grid.
 * It detects vertical, horizontal, and diagonal matches of three or more identical symbols,
 * calculates payouts, and triggers visual effects in the Slots game.
 */
public class CheckIfMatch {
    private final SlotMachine[][] field = new SlotMachine[4][6];
    private final ArrayList<SlotMachine> slotlist = new ArrayList<>();
    private final Slots slots;
    private final ArrayList<Point[]> allpoints = new ArrayList<>();
    public int total;

    /**
     * Constructs a CheckIfMatch object associated with a given Slots instance.
     *
     * @param slots The Slots game instance to interact with.
     */
    public CheckIfMatch(Slots slots) {
        this.slots = slots;
    }

    /**
     * Adds a SlotMachine instance to the internal list for match checking.
     * Once 15 slots are added, it triggers the matching logic.
     *
     * @param slotmachine The SlotMachine object to add.
     */
    public void addtocounter(SlotMachine slotmachine) {
        slotlist.add(slotmachine);
        if (slotlist.size() == 15) {
            addintogrid();
        }
    }

    /**
     * Copies collected slots into a 2D grid representation and initiates the match checking process.
     */
    private void addintogrid() {
        for (SlotMachine slot : slotlist) {
            field[slot.row][slot.col] = slot;
        }
        checkit();
    }

    /**
     * Checks for matches in vertical, horizontal, and diagonal directions.
     * Calculates payouts accordingly, triggers line drawing effects,
     * and updates the Slots game state.
     */
    /**
     * the checking methods are made part by me part by chat, i always created the method (checking system) and i awas always missing some small part in it and chatt added it
     */
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
                        total += slots.bet;
                    }
                    count = 1;
                }
            }
            if (count >= 3) {
                found = true;
                drawVerticalMatch(3, col, count);
                total += slots.bet;
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
                        switch (count) {
                            case 3 -> total += slots.bet;
                            case 4 -> total += slots.bet * 2;
                            case 5 -> total += slots.bet * 3;
                        }
                    }
                    count = 1;
                }
            }
            if (count >= 3) {
                found = true;
                drawHorizontalMatch(row, 5, count);
                switch (count) {
                    case 3 -> total += slots.bet;
                    case 4 -> total += slots.bet * 2;
                    case 5 -> total += slots.bet * 3;
                }
            }
        }

        // Diagonal ↘ matches (3+)
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
                    total += slots.bet * 2;
                }
            }
        }

        // Diagonal ↙ matches (3+)
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
                    total += slots.bet * 2;
                }
            }
        }

        if (!found) {
            System.out.println("No matches found.");
        }

        // Finalize payout and update Slots instance
        slots.addpoints(allpoints);
        slots.payout = total;
        slots.addmoney();
        total = 0;

        slotlist.clear();
        clearGrid();
        allpoints.clear();
    }

    /**
     * Draws a vertical match line on the slot grid.
     *
     * @param endRow The last row of the match.
     * @param col The column where the match occurred.
     * @param count The number of matched slots.
     */
    private void drawVerticalMatch(int endRow, int col, int count) {
        SlotMachine[] match = new SlotMachine[count];
        for (int i = 0; i < count; i++) {
            match[i] = field[endRow - i][col];
        }
        drawMatchLine(match);
    }

    /**
     * Draws a horizontal match line on the slot grid.
     *
     * @param row The row where the match occurred.
     * @param endCol The last column of the match.
     * @param count The number of matched slots.
     */
    private void drawHorizontalMatch(int row, int endCol, int count) {
        SlotMachine[] match = new SlotMachine[count];
        for (int i = 0; i < count; i++) {
            match[i] = field[row][endCol - i];
        }
        drawMatchLine(match);
    }

    /**
     * Records the points for matched slots and triggers repaint for visual effects.
     *
     * @param slotsToMatch The slots involved in the match.
     */
    //chat method
    private void drawMatchLine(SlotMachine... slotsToMatch) {
        ArrayList<Point> points = new ArrayList<>();
        for (SlotMachine s : slotsToMatch) {
            if (s != null) {
                points.add(convertToFrameCenter(s));
            }
        }
        allpoints.add(points.toArray(new Point[6]));
        slots.drawline = true;
        slots.repaint();
    }

    /**
     * Converts the center point of a SlotMachine component to the coordinate space of the Slots container.
     *
     * @param slot The SlotMachine component.
     * @return The converted center point relative to the Slots container.
     */
    public Point convertToFrameCenter(SlotMachine slot) {
        //chat
        if (slot != null) {
            return SwingUtilities.convertPoint(
                    slot,
                    new Point(slot.getWidth() / 2, slot.getHeight() / 2),
                    slots
            );
        }
        return null;
    }

    /**
     * Clears the internal grid of SlotMachine references.
     */
    private void clearGrid() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = null;
            }
        }
    }
}
