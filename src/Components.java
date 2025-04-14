import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;

public class Components {
    public void addnumbers(int[] numbers) {
        numbers[0] = 0;
        numbers[1] = 26;
        numbers[2] = 3;
        numbers[3] = 35;
        numbers[4] = 12;
        numbers[5] = 28;
        numbers[6] = 7;
        numbers[7] = 29;
        numbers[8] = 18;
        numbers[9] = 22;
        numbers[10] = 9;
        numbers[11] = 31;
        numbers[12] = 14;
        numbers[13] = 20;
        numbers[14] = 1;
        numbers[15] = 33;
        numbers[16] = 16;
        numbers[17] = 24;
        numbers[18] = 5;
        numbers[19] = 10;
        numbers[20] = 23;
        numbers[21] = 8;
        numbers[22] = 30;
        numbers[23] = 11;
        numbers[24] = 36;
        numbers[25] = 13;
        numbers[26] = 27;
        numbers[27] = 6;
        numbers[28] = 34;
        numbers[29] = 17;
        numbers[30] = 25;
        numbers[31] = 2;
        numbers[32] = 21;
        numbers[33] = 4;
        numbers[34] = 19;
        numbers[35] = 15;
        numbers[36] = 32;
    }

    public void addbuttons(JPanel panel, HashMap buttons, Roulette roulette) {

        //number files
        JButton zero = new JButton("");
        zero.setOpaque(false);
        zero.setContentAreaFilled(false);
        zero.setBorderPainted(false);
        zero.setFocusPainted(false);
        zero.setBounds(105, 0, 315, 110);
        ImageIcon icon = new ImageIcon("src/Images/Roulette/RouletteButtons/button_0_half_circle_top_final.png");
        zero.setIcon(icon);
        panel.add(zero);
        buttons.put(zero, 0);
        JButton red = new JButton("");
        red.setOpaque(false);
        red.setContentAreaFilled(false);
        red.setBorderPainted(false);
        red.setFocusPainted(false);
        red.setBounds(105, 770, 315, 110);
        File folder = new File("src/Images/Roulette/RouletteButtons");
        File[] imageFiles = folder.listFiles((dir, name) -> name.matches("button_\\d+\\.png"));

        Arrays.sort(imageFiles, (f1, f2) -> {
            int num1 = extractNumber(f1.getName());
            int num2 = extractNumber(f2.getName());
            return Integer.compare(num1, num2);
        });

        for (File file : imageFiles) {
            System.out.println(file.getName());
        }

        int x = 1;
        int y = 2;

        for (int i = 0; i < imageFiles.length; i++) {
            JButton button = new JButton("");
            if (x == 4) {
                x = 1;
                y++;
            }

            try {
                BufferedImage image1 = ImageIO.read(imageFiles[i]);
                button.setLayout(null);
                button.setIcon(new ImageIcon(image1));
                button.setBounds(x * 105, y * 55, 105, 55);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setFocusable(false);
                button.addActionListener(new NumbersListener(roulette));
                panel.add(button);
                buttons.put(button, i);
                x++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private int extractNumber(String filename) {
        try {
            String num = filename.replaceAll("[^\\d]", "");
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
