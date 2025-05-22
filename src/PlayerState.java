public class PlayerState {
    public boolean hasBet = false;
    public boolean hasCard = false;
    public Pointer position;

    public PlayerState(Pointer position) {
        this.position = position;
    }
}
