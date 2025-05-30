package BlackJackPck;

/**
 * Represents the state of a player in the Blackjack game.
 * Contains all relevant game data for each player, including betting,
 * card values, actions, and status flags.
 */
public class PlayerState {

    /** Indicates whether the player has placed a bet in the current round. */
    public boolean hasBet = false;

    /** Indicates whether the player has received their first card. */
    public boolean hasCard = false;

    /** The current total value of the player's hand. */
    public int value = 0;

    /** The name or identifier of the player (e.g., "1", "2", "Dealer"). */
    public String name = "";

    /** Tracks the current phase/state of the player (unused or reserved for future use). */
    public int phase = 0;

    /** The amount of money the player has bet in the current round. */
    public int bet = 0;

    /** Indicates whether the player has received a second card. */
    public boolean hassecondcard = false;

    /** Indicates whether the player has already made a hit or stay decision. */
    public boolean hashitstay = false;

    /** Indicates whether the player has chosen to hit (request another card). */
    public boolean hit = false;

    /** Indicates whether the player has chosen to stay (not request more cards). */
    public boolean stay = false;

    /** Indicates whether the player has completed their actions for the round. */
    public boolean playerisdone = false;

    /** The total amount of money the player currently has. */
    public int money = 1000;

    /** Indicates whether the player has received a third card. */
    public boolean hasthiredcard = false;

    /** The screen position where the player's cards or avatar is rendered. */
    public Pointer position;

    /**
     * Constructs a PlayerState with a given table position.
     *
     * @param position The position on the screen for this player.
     */
    public PlayerState(Pointer position) {
        this.position = position;
    }

    /**
     * Resets the player's state at the beginning of a new round.
     * All round-related flags and values are reset, but money is preserved.
     */
    public void reset() {
        value = 0;
        bet = 0;
        hasBet = false;
        hasCard = false;
        hassecondcard = false;
        hit = false;
        stay = false;
        hashitstay = false;
        playerisdone = false;
    }
}
