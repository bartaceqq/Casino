import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SetupCards {

    public void extractFiles(BufferedImage[][] cards) {
        String[] suits = {"C", "D", "H", "S"};
        String basePath = "src/images/BlackJack/Cards/";

        for (int suitIndex = 0; suitIndex < suits.length; suitIndex++) {
            String suit = suits[suitIndex];
            int suitNumber = suitIndex + 1; // C=1, D=2, H=3, S=4

            File folder = new File(basePath + suit);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files != null) {
                // Sort alphabetically
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
                    return -1; // invalid
                }
        }
    }

    public static void main(String[] args) {
        int maxRank = 11; // ranks 2–11 (where 10 holds J, Q, K, and 11 is Ace)
        int maxSuit = 4;  // suits 1–4 (C, D, H, S)

        BufferedImage[][] cards = new BufferedImage[maxRank + 1][maxSuit + 1]; // [rank][suit]
        SetupCards setup = new SetupCards();
        setup.extractFiles(cards);

        System.out.println("Card loading complete.");
    }
}
