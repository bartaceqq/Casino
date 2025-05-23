import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SetupCards {
    public void extractFiles(BufferedImage[][] cards) {
        File folder = new File("src/Images/BlackJack/Cards");

        File[] cardFiles = folder.listFiles();
        if (cardFiles == null) return;

        for (File file : cardFiles) {
            if (!file.getName().endsWith(".png")) continue;

            String name = file.getName().replace(".png", ""); // card_clubs_08

            if (!name.startsWith("card_")) continue;

            String[] parts = name.substring(5).split("_"); // [clubs, 08]

            int suit = switch (parts[0]) {
                case "clubs" -> 0;
                case "diamonds" -> 1;
                case "hearts" -> 2;
                case "spades" -> 3;
                default -> -1;
            };

            int rank = switch (parts[1]) {
                case "J" -> 9;
                case "Q" -> 10;
                case "K" -> 11;
                case "A" -> 12;
                default -> Integer.parseInt(parts[1]) - 2;
            };

            if (suit >= 0 && rank >= 0 && rank < 13) {
                try {
                    cards[rank][suit] = ImageIO.read(file);
                    System.out.println("Loaded: " + name + " to cards[" + rank + "][" + suit + "]");
                } catch (IOException e) {
                    System.out.println("Failed to load " + name);
                    e.printStackTrace();
                }
            }
        }
    }
}
