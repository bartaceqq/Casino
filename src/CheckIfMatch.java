import java.util.ArrayList;

public class CheckIfMatch {
    private final SlotMachine[][] field = new SlotMachine[4][6]; // more room to avoid out-of-bounds
    private final ArrayList<SlotMachine> slotlist = new ArrayList<>();

    public void addtocounter(SlotMachine slotmachine) {
        slotlist.add(slotmachine);
        if (slotlist.size() == 15) {
            addintogrid();
        }
    }
    public void addintogrid() {
        for (SlotMachine slot : slotlist) {
                field[slot.row][slot.col] = slot;
        }
        checkit();
    }

    public void checkit() {
        boolean matchFound = false;

       //chat
        //vertical
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 1 + 1; row++) { // max row 1 to prevent index 3 out of bounds
                if (isMatch(field[row][col], field[row + 1][col], field[row + 2][col])) {
                    System.out.println("naslo vertical");
                    matchFound = true;
                }
            }
        }

        // horizontal
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) { // max col 2 to prevent index 5 out of bounds
                if (isMatch(field[row][col], field[row][col + 1], field[row][col + 2])) {
                    System.out.println("naslo horizontal");
                    matchFound = true;
                }
            }
        }


        if (!matchFound) {
            System.out.println("No matches found.");
        }
        slotlist.clear();
        clearGrid();
    }

    private boolean isMatch(SlotMachine a, SlotMachine b, SlotMachine c) {
        return a != null && b != null && c != null && a.thenumber == b.thenumber && b.thenumber == c.thenumber;
    }
//konec chatu

    private void clearGrid() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = null;
            }
        }
    }
}
