import javax.swing.text.Position;
import java.awt.*;
import java.awt.image.BufferedImage;
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

                // Parse default money
                for (String l : lines) {
                    if (l.startsWith("PlayerDefMoney")) {
                        playerdef = Integer.parseInt(l.split("/")[1]);
                        break;
                    }
                }

                // Rewrite the file with updated player money
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                for (String l : lines) {
                    if (l.startsWith("Player1Money")) {
                        l = "Player1Money/" + playerdef;
                        p1 = playerdef;
                    } else if (l.startsWith("Player2Money")) {
                        l = "Player2Money/" + playerdef;
                        p2 = playerdef;
                    } else if (l.startsWith("Player3Money")) {
                        l = "Player3Money/" + playerdef;
                        p3 = playerdef;
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
                p1 = Integer.parseInt(split[1]);
                String line2 = br.readLine();
                String[] split2 = line2.split("/");
                p2 = Integer.parseInt(split2[1]);
                String line3 = br.readLine();
                String[] split3 = line3.split("/");
                p3 = Integer.parseInt(split3[1]);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void moneyread(PlayerState p1, PlayerState p2, PlayerState p3) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/DataSaver"));
            br.readLine();
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

    public static void main(String[] args) {
        Pointer po1 = new Pointer(5, 4);
        PlayerState p1 = new PlayerState(po1);
        Pointer po2 = new Pointer(5, 4);
        PlayerState p2 = new PlayerState(po2);
        Pointer po3 = new Pointer(5, 4);
        PlayerState p3 = new PlayerState(po3);
        MoneyLoaderBlackJack money = new MoneyLoaderBlackJack();
        money.moneyread(p1, p2, p3);
        System.out.println(p1.money);
        System.out.println(p2.money);
        System.out.println(p3.money);
    }
}
