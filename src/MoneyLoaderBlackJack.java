import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MoneyLoaderBlackJack {
    private int playerdef = 0;
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;

    public void loadmoney(boolean firstrun, int p1, int p2, int p3) {
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
                br.readLine();
                String line = br.readLine();
                String[] split = line.split("/");
                this.p1 = Integer.parseInt(split[1]);
                String line2 = br.readLine();
                String[] split2 = line2.split("/");
                this.p2 = Integer.parseInt(split2[1]);
                String line3 = br.readLine();
                String[] split3 = line3.split("/");
                this.p3 = Integer.parseInt(split3[1]);
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
                String[] split = line.split("/");
                switch (split[0]) {
                    case "Player1Money":
                        p1.money = Integer.parseInt(split[1]);
                        break;
                    case "Player2Money":
                        p2.money = Integer.parseInt(split[1]);
                        break;
                    case "Player3Money":
                        p3.money = Integer.parseInt(split[1]);
                        break;
                }
            }
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
                    lines.add("Player1Money/" + p1.money);
                } else if (line.startsWith("Player2Money")) {
                    lines.add("Player2Money/" + p2.money);
                } else if (line.startsWith("Player3Money")) {
                    lines.add("Player3Money/" + p3.money);
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
