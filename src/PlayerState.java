public class PlayerState {
    public boolean hasBet = false;
    public boolean hasCard = false; // This might be better as a counter for cards dealt
    public int value = 0; // Current score of the player
    public int phase = 0;
    public boolean hassecondcard = false; // Consider removing or refactoring if 'value' is used as card count
    public boolean hasthiredcard = false; // Consider removing or refactoring if 'value' is used as card count
    public Pointer position;

    public PlayerState(Pointer position) {
        this.position = position;
    }
}