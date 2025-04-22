import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Components {
    public void doredandblack(JButton red, JButton black, JPanel betpanel, Roulette roulette) {
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betpanel.setVisible(true);
                roulette.colorchoose = true;
                roulette.isblackie = false; // red selected
            }
        });
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betpanel.setVisible(true);
                roulette.colorchoose = true;
                roulette.isblackie = true; // black selected
            }
        });
    }
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
    public void setupblackredmap(HashMap<Integer, Boolean> map) {
        // Black = true, Red = false
        int[] blackNumbers = {
                2, 4, 6, 8, 10, 11, 13, 15,
                17, 20, 22, 24, 26, 28, 29, 31, 33, 35
        };

        int[] redNumbers = {
                1, 3, 5, 7, 9, 12, 14, 16,
                18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        };

        for (int num : blackNumbers) {
            map.put(num, true); // black = true
        }

        for (int num : redNumbers) {
            map.put(num, false); // red = false
        }

        // 0 is green; you can leave it unassigned or handle it explicitly
    }
    public void souter(int money, int blackbet){
        System.out.println("proslo");
        System.out.println(blackbet + " blackbetik");
    }
    public void souter1(int money, int redbet){
        System.out.println("proslo1");
        System.out.println(redbet + "redbetik");
    }
    public int blackandredmoneymaker(int blackbet, int redbet, HashMap<Integer, Boolean> map, int winningnumber, int currentMoney, JPanel panel, Roulette roulette) {
        if (roulette.isblackie) {
            System.out.println("cerna prosla");
            // Black bet logic
            if (map.get(winningnumber)) {
                // Black number
                System.out.println("BLACKBETIK  " + blackbet);
                return currentMoney + (int)(blackbet * 1.7);
            } else {
                // Not black, loss
                return currentMoney; // loss
            }
        } else {
            // Red bet logic
            if (!map.get(winningnumber)) {
                // Red number
                return currentMoney +(int) (redbet * 1.7); // win the red bet
            } else {
                // Not red, loss
                return currentMoney; // loss
            }
        }
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
        ImageIcon icone = new ImageIcon("src/Images/Roulette/RouletteButtons/button_red.png");
        red.setIcon(icone);
        red.setOpaque(false);
        red.setContentAreaFilled(false);
        red.setBorderPainted(false);
        red.setFocusPainted(false);
        red.setBounds(105, 770, 157, 70);
        panel.add(red);
        JButton black = new JButton("");
        ImageIcon iconka = new ImageIcon("src/Images/Roulette/RouletteButtons/button_black.png");
        black.setIcon(iconka);
        black.setOpaque(false);
        black.setContentAreaFilled(false);
        black.setBorderPainted(false);
        black.setFocusPainted(false);
        black.setBounds(263, 770, 157, 70);
        panel.add(black);
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
                buttons.put(button, i+1);
                x++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        panel.revalidate();
        panel.repaint();
        doredandblack(red, black, roulette.betpanel,roulette);
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
