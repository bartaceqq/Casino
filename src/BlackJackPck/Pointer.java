package BlackJackPck;

import java.awt.*;

/**
 * A specialized Point class representing the position of a pointer in a Blackjack game,
 * with additional flags indicating whether specific cards have been dealt.
 */
//this whole class is like idea from the chat but i did it by myself
public class Pointer extends Point {

    /**
     * Indicates whether the first card has been dealt.
     */
    public boolean dealtone;

    /**
     * Indicates whether the second card has been dealt.
     */
    public boolean dealttwo;

    /**
     * Indicates whether the third card has been dealt.
     */
    public boolean dealtthree;

    /**
     * Constructs a Pointer at the specified (x, y) coordinate.
     *
     * @param x the X coordinate of the pointer.
     * @param y the Y coordinate of the pointer.
     */
    public Pointer(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
