public class PlayerState {
    public boolean hasBet = false;
    public boolean hasCard = false;
    public int cardnum = 0;
    public boolean hassecondcard = false;
    public boolean hasthiredcard = false;
    public Pointer position;

    public PlayerState(Pointer position) {
        this.position = position;
    }
}
