package MoneyLoaders;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import BlackJackPck.PlayerState;

/**
 * Handles loading and saving player money data for the Blackjack game.
 * Reads from and writes to a data file to persist player money between sessions.
 */
public class MoneyLoaderBlackJack {

    private int playerdef = 0;
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;

    /**
     * Loads money values for players from the data file.
     *
     * If this is the first run, it initializes all player money values to the default
     * money amount found in the file and saves them back.
     * Otherwise, it reads the current money values for players 1 to 3.
     *
     * @param firstrun true if the game is starting fresh and players should be set to default money
     * @param p1 Initial money value for player 1 (not used in method, consider refactoring)
     * @param p2 Initial money value for player 2 (not used in method)
     * @param p3 Initial money value for player 3 (not used in method)
     */
    public void loadmoney(boolean firstrun, Integer p1, Integer p2, Integer p3) {
        if (firstrun) {
            try {
                File file = new File("src/DataSaver");
                BufferedReader br = new BufferedReader(new FileReader(file));

                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                br.close();

                // Find the default player money line
                for (String l : lines) {
                    if (l.startsWith("PlayerDefMoney")) {
                        playerdef = Integer.parseInt(l.split("/")[1]);
                        break;
                    }
                }

                // Overwrite player money lines with default money
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (String l : lines) {
                    if (l.startsWith("Player1Money")) {
                        l = "Player1Money/" + playerdef;
                        this.p1 = playerdef;
                    } else if (l.startsWith("Player2Money")) {
                        l = "Player2Money/" + playerdef;
                        this.p2 = playerdef;
                    } else if (l.startsWith("Player3Money")) {
                        l = "Player3Money/" + playerdef;
                        this.p3 = playerdef;
                    }
                    bw.write(l);
                    bw.newLine();
                }
                bw.close();

            } catch (Exception e) {
                System.out.println("Error in MoneyLoaders.MoneyLoaderBlackJack constructor: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                //chat
                BufferedReader br = new BufferedReader(new FileReader("src/DataSaver"));
                br.readLine();  // skip first line (default money)
                String line = br.readLine();
                if (line != null) {
                    String[] split = line.split("/");
                    if (split.length > 1) this.p1 = Integer.parseInt(split[1]);
                }
                String line2 = br.readLine();
                if (line2 != null) {
                    String[] split2 = line2.split("/");
                    if (split2.length > 1) this.p2 = Integer.parseInt(split2[1]);
                }
                String line3 = br.readLine();
                if (line3 != null) {
                    String[] split3 = line3.split("/");
                    if (split3.length > 1) this.p3 = Integer.parseInt(split3[1]);
                }
                br.close();
                //end of chat
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the player money values from the data file and sets them into the provided PlayerState objects.
     *
     * @param p1 PlayerState object for player 1 (can be null if player does not exist)
     * @param p2 PlayerState object for player 2 (can be null)
     * @param p3 PlayerState object for player 3 (can be null)
     */
    public void moneyread(PlayerState p1, PlayerState p2, PlayerState p3) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/DataSaver"));
            br.readLine(); // skip default money line
            while (br.ready()) {
                String line = br.readLine();
                if (line == null) continue;
                String[] split = line.split("/");
                if (split.length < 2) continue;
                switch (split[0]) {
                    case "Player1Money":
                        if (p1 != null) p1.money = Integer.parseInt(split[1]);
                        break;
                    case "Player2Money":
                        if (p2 != null) p2.money = Integer.parseInt(split[1]);
                        break;
                    case "Player3Money":
                        if (p3 != null) p3.money = Integer.parseInt(split[1]);
                        break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current money values of the players into the data file.
     * Updates only the player money lines, leaving other data intact.
     *
     * @param p1 PlayerState object for player 1 (can be null)
     * @param p2 PlayerState object for player 2 (can be null)
     * @param p3 PlayerState object for player 3 (can be null)
     */
    public void saveMoney(PlayerState p1, PlayerState p2, PlayerState p3) {
        try {
            File file = new File("src/DataSaver");
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            //chat
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Player1Money")) {
                    lines.add(p1 != null ? "Player1Money/" + p1.money : line);
                } else if (line.startsWith("Player2Money")) {
                    lines.add(p2 != null ? "Player2Money/" + p2.money : line);
                } else if (line.startsWith("Player3Money")) {
                    lines.add(p3 != null ? "Player3Money/" + p3.money : line);
                } else {
                    lines.add(line);
                }
            }
            br.close();
            //end of chat
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error in saveMoney: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
