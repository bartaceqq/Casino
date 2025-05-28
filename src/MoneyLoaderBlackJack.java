import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MoneyLoaderBlackJack {
    private int playerdef = 0;
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;

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

                for (String l : lines) {
                    if (l.startsWith("PlayerDefMoney")) {
                        playerdef = Integer.parseInt(l.split("/")[1]);
                        break;
                    }
                }

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
                System.out.println("Error in MoneyLoaderBlackJack constructor: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/DataSaver"));
                br.readLine();  // skip first line
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    // NEW METHOD TO SAVE MONEY
    public void saveMoney(PlayerState p1, PlayerState p2, PlayerState p3) {
        try {
            File file = new File("src/DataSaver");
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
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
