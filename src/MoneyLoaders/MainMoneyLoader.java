package MoneyLoaders;

import java.io.*;

/**
 * A utility class for loading and saving the amount of money
 * to and from a local file named "src/MainDataSaver".
 *
 * The file is expected to contain a single line formatted as:
 * "money/<amount>"
 */
public class MainMoneyLoader {

    /**
     * Loads the money amount from the file "src/MainDataSaver".
     *
     * @return The money amount read from the file.
     *         Returns 0 if the file is missing or an error occurs.
     */
    public int loadMoney() {
        int money = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/MainDataSaver"));
            String line = br.readLine();
            String[] split = line.split("/");
            money = Integer.parseInt(split[1]);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return money;
    }

    /**
     * Saves the specified money amount to the file "src/MainDataSaver".
     * The money is saved in the format: "money/<amount>".
     *
     * @param money The amount of money to save.
     */
    public void savemoney(int money) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/MainDataSaver"));
            bw.write("money/" + money);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
