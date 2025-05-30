package RouletPck;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Provides utility methods to set up and manage components related to the Roulette game.
 * <p>
 * This class includes methods for configuring buttons, mapping colors to numbers,
 * and processing bets related to red and black.
 * </p>
 */
public class Components {

    /**
     * Adds action listeners to the red and black buttons to handle color choice for betting.
     * When either button is clicked, the bet panel is made visible and the roulette state
     * is updated to reflect the chosen color.
     *
     * @param red      the JButton representing the red color.
     * @param black    the JButton representing the black color.
     * @param betpanel the JPanel containing the betting interface.
     * @param roulette the Roulette game instance to update state on.
     */
    public void doredandblack(JButton red, JButton black, JPanel betpanel, Roulette roulette) {
        red.addActionListener(e -> {
            betpanel.setVisible(true);
            roulette.colorchoose = true;
            roulette.isblackie = false; // red selected
        });
        black.addActionListener(e -> {
            betpanel.setVisible(true);
            roulette.colorchoose = true;
            roulette.isblackie = true; // black selected
        });
    }

    /**
     * Populates an integer array with roulette numbers in a specific order.
     *
     * @param numbers an integer array with length at least 37 to be filled with roulette numbers.
     */
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

    /**
     * Sets up a map associating roulette numbers with their color.
     * Black numbers are mapped to {@code true}, red numbers to {@code false}.
     * Zero (0) is not assigned, as it is green.
     *
     * @param map a HashMap where the keys are integers representing numbers on the roulette wheel,
     *            and values are Booleans where {@code true} = black, {@code false} = red.
     */
    public void setupblackredmap(HashMap<Integer, Boolean> map) {
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

        // 0 is green; no assignment made here
    }

    /**
     * Debug method that prints the current money and black bet amount.
     *
     * @param money    the current money amount.
     * @param blackbet the bet amount placed on black.
     */
    public void souter(int money, int blackbet) {
        System.out.println("proslo");
        System.out.println(blackbet + " blackbetik");
    }

    /**
     * Debug method that prints the current money and red bet amount.
     *
     * @param money   the current money amount.
     * @param redbet  the bet amount placed on red.
     */
    public void souter1(int money, int redbet) {
        System.out.println("proslo1");
        System.out.println(redbet + "redbetik");
    }

    /**
     * Calculates and returns the new money amount based on bets on black or red.
     * Applies a 1.7x multiplier on the winning bet.
     *
     * @param blackbet     the amount bet on black.
     * @param redbet       the amount bet on red.
     * @param map          a map of roulette numbers to their color (black/red).
     * @param winningnumber the winning roulette number.
     * @param currentMoney the player's current money before payout.
     * @param panel        the betting panel (unused in this method).
     * @param roulette     the Roulette instance, which indicates if black or red was chosen.
     * @return the updated money amount after applying the bet results.
     */
    public int blackandredmoneymaker(int blackbet, int redbet, HashMap<Integer, Boolean> map,
                                     int winningnumber, int currentMoney, JPanel panel, Roulette roulette) {
        if (roulette.isblackie) {
            System.out.println("cerna prosla");
            if (map.getOrDefault(winningnumber, false)) {
                System.out.println("BLACKBETIK  " + blackbet);
                return currentMoney + (int) (blackbet * 1.7);
            } else {
                return currentMoney; // loss
            }
        } else {
            if (!map.getOrDefault(winningnumber, true)) {
                return currentMoney + (int) (redbet * 1.7);
            } else {
                return currentMoney; // loss
            }
        }
    }

    /**
     * Adds roulette number buttons and color buttons (red/black) to the given panel.
     * Buttons are assigned images and action listeners, and stored in the provided map.
     *
     * @param panel    the JPanel to add buttons to.
     * @param buttons  a HashMap to associate buttons with roulette numbers.
     * @param roulette the Roulette game instance for handling button actions.
     */
    public void addbuttons(JPanel panel, HashMap<JButton, Integer> buttons, Roulette roulette) {

        // Zero button setup
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

        // Red button setup
        JButton red = new JButton("");
        ImageIcon icone = new ImageIcon("src/Images/Roulette/RouletteButtons/button_red.png");
        red.setIcon(icone);
        red.setOpaque(false);
        red.setContentAreaFilled(false);
        red.setBorderPainted(false);
        red.setFocusPainted(false);
        red.setBounds(105, 770, 157, 70);
        panel.add(red);

        // Black button setup
        JButton black = new JButton("");
        ImageIcon iconka = new ImageIcon("src/Images/Roulette/RouletteButtons/button_black.png");
        black.setIcon(iconka);
        black.setOpaque(false);
        black.setContentAreaFilled(false);
        black.setBorderPainted(false);
        black.setFocusPainted(false);
        black.setBounds(263, 770, 157, 70);
        panel.add(black);

        // Load number buttons from image files
        File folder = new File("src/Images/Roulette/RouletteButtons");
        File[] imageFiles = folder.listFiles((dir, name) -> name.matches("button_\\d+\\.png"));

        if (imageFiles != null) {
            Arrays.sort(imageFiles, (f1, f2) -> {
                int num1 = extractNumber(f1.getName());
                int num2 = extractNumber(f2.getName());
                return Integer.compare(num1, num2);
            });

            int x = 1;
            int y = 2;

            for (File file : imageFiles) {
                JButton button = new JButton("");
                if (x == 4) {
                    x = 1;
                    y++;
                }

                try {
                    BufferedImage image1 = ImageIO.read(file);
                    button.setLayout(null);
                    button.setIcon(new ImageIcon(image1));
                    button.setBounds(x * 105, y * 55, 105, 55);
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                    button.setFocusable(false);
                    button.addActionListener(new NumbersListener(roulette));
                    panel.add(button);
                    buttons.put(button, extractNumber(file.getName()));
                    x++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        panel.revalidate();
        panel.repaint();

        doredandblack(red, black, roulette.betpanel, roulette);
    }

    /**
     * Extracts the numeric part of a filename assuming it contains digits,
     * e.g. "button_12.png" will return 12.
     *
     * @param filename the name of the file.
     * @return the extracted number or -1 if no valid number is found.
     */
    public int extractNumber(String filename) {
        try {
            String num = filename.replaceAll("[^\\d]", "");
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
