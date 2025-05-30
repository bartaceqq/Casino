package BlackJackPck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetupCardsTest {

    SetupCards setup = new SetupCards();

    @Test
    void testGetRankNumber_NumericCards() {
        assertEquals(2, setup.getRankNumber("2"));
        assertEquals(5, setup.getRankNumber("5"));
        assertEquals(10, setup.getRankNumber("10"));
    }

    @Test
    void testGetRankNumber_FaceCards() {
        assertEquals(10, setup.getRankNumber("J"));
        assertEquals(10, setup.getRankNumber("Q"));
        assertEquals(10, setup.getRankNumber("K"));
    }
    @Test
    void testGetRankNumber_InvalidInput() {
        assertEquals(-1, setup.getRankNumber("Z"));    // not a valid card
        assertEquals(-1, setup.getRankNumber(""));     // empty string
        assertEquals(-1, setup.getRankNumber("1"));    // 1 is not a valid rank in Blackjack here
        assertEquals(-1, setup.getRankNumber("joker"));// invalid name
    }

    @Test
    void testGetRankNumber_Ace() {
        assertEquals(11, setup.getRankNumber("A"));
    }
}