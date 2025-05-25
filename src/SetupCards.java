import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SetupCards {
    public void extractFiles(BufferedImage[][] cards) {
        File folder = new File("src/Images/BlackJack/Cards");

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Card image directory not found.");
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") && name.startsWith("card_"));
        if (files == null) return;

        for (File file : files) {
            try {
                String filename = file.getName().replace(".png", ""); // remove extension
                String[] parts = filename.split("_"); // expect "card_suit_rank"

                if (parts.length != 3) {
                    System.err.println("Skipping malformed file: " + filename);
                    continue;
                }

                String suitStr = parts[1].toLowerCase();
                String rankStr = parts[2].toUpperCase();

                int suit = switch (suitStr) {
                    case "clubs" -> 0;
                    case "diamonds" -> 1;
                    case "hearts" -> 2;
                    case "spades" -> 3;
                    default -> -1;
                };

                int rank;
                switch (rankStr) {
                    case "A" -> rank = 12; // Ace as highest index
                    case "K" -> rank = 11;
                    case "Q" -> rank = 10;
                    case "J" -> rank = 9;
                    default -> {
                        try {
                            int num = Integer.parseInt(rankStr);
                            rank = num - 2; // 2–10 to 0–8
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid rank: " + rankStr);
                            continue;
                        }
                    }
                }

                if (suit < 0 || rank < 0 || rank >= 13) {
                    System.err.println("Invalid card: " + filename);
                    continue;
                }

                BufferedImage img = ImageIO.read(file);
                cards[rank][suit] = img;

            } catch (Exception e) {
                System.err.println("Failed to load card image: " + file.getName());
                e.printStackTrace();
            }
        }
    }

}
