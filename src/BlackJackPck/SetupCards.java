package BlackJackPck;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Responsible for loading card images from the file system into a 2D array.
 * The cards are stored by rank and suit for use in the Blackjack game.
 */
public class SetupCards {

    /**
     * Loads card image files into the provided 2D BufferedImage array.
     * The array is indexed by rank and suit.
     *
     * Assumes a folder structure under "src/images/BlackJackPck.BlackJack/Cards/"
     * with subfolders named "C", "D", "H", "S" representing suits.
     * Card image files are expected to be PNGs named by their rank (e.g., "2.png", "A.png", "J.png").
     *
     * @param cards 2D array where cards[rank][suit] will be populated with card images.
     *              Rank is from 1 to maxRank (including special handling of face cards and ace),
     *              Suit is from 1 to 4 (Clubs=1, Diamonds=2, Hearts=3, Spades=4).
     */
    public void extractFiles(BufferedImage[][] cards) {
        String[] suits = {"C", "D", "H", "S"};
        String basePath = "src/images/BlackJack/Cards/";

        for (int suitIndex = 0; suitIndex < suits.length; suitIndex++) {
            String suit = suits[suitIndex];
            int suitNumber = suitIndex + 1; // Clubs=1, Diamonds=2, Hearts=3, Spades=4

            File folder = new File(basePath + suit);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files != null) {
                // Sort files alphabetically for consistent loading order
                Arrays.sort(files, (f1, f2) -> f1.getName().compareTo(f2.getName()));

                for (File file : files) {
                    String fileName = file.getName().replace(".png", "");
                    int rankNumber = getRankNumber(fileName);

                    if (rankNumber > 0 && rankNumber < cards.length && suitNumber < cards[rankNumber].length) {
                        try {
                            cards[rankNumber][suitNumber] = ImageIO.read(file);
                            System.out.println("Loaded: rank " + rankNumber + ", suit " + suitNumber + " from " + file.getPath());
                        } catch (IOException e) {
                            System.err.println("Failed to load: " + file.getPath());
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Invalid rank or suit mapping for file: " + file.getName());
                    }
                }
            } else {
                System.err.println("Folder not found or empty: " + folder.getPath());
            }
        }
    }

    /**
     * Converts a card rank file name to its corresponding rank number used in the game.
     * Face cards J, Q, K map to 10, Ace (A) maps to 11.
     *
     * @param fileName The card file name without extension (e.g., "2", "A", "J").
     * @return The integer rank used in the cards array, or -1 if invalid.
     */
    private int getRankNumber(String fileName) {
        switch (fileName) {
            case "A":
                return 11;
            case "J":
            case "Q":
            case "K":
                return 10;
            default:
                try {
                    return Integer.parseInt(fileName);
                } catch (NumberFormatException e) {
                    System.err.println("Unknown rank file name: " + fileName);
                    return -1; // invalid rank
                }
        }
    }

    /**
     * Main method to test card loading.
     * Initializes the cards array and invokes extraction from the filesystem.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        int maxRank = 11; // ranks 2–11 (where 10 holds J, Q, K, and 11 is Ace)
        int maxSuit = 4;  // suits 1–4 (C, D, H, S)

        BufferedImage[][] cards = new BufferedImage[maxRank + 1][maxSuit + 1]; // [rank][suit]
        SetupCards setup = new SetupCards();
        setup.extractFiles(cards);

        System.out.println("Card loading complete.");
    }
}
