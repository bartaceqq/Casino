package BlackJackPck;

import MainMenicko.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Utility class for setting up buttons and labels in the Blackjack game GUI.
 * Provides methods to create game controls such as betting, hit/stay, continue, and exit buttons.
 */
public class ButtonSetup {

    /**
     * Creates and returns the "Add Bet" button.
     *
     * @param pixel     The font to use.
     * @param mainpanel The panel to which the button will be added.
     * @param blackJack The BlackJack instance for accessing game state.
     * @return The configured "Add Bet" button.
     */
    public JButton setupaddbutton(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton addbet = new JButton();
        ImageIcon icon = new ImageIcon("src/Images/BlackJack/plus.png");
        addbet.setIcon(icon);
        addbet.setContentAreaFilled(false);
        addbet.setFocusPainted(false);
        addbet.setBorderPainted(false);
        addbet.addMouseListener(new SecHovering(1, null));
        addbet.setHorizontalAlignment(SwingConstants.CENTER);
        addbet.setVerticalAlignment(SwingConstants.CENTER);
        addbet.setFont(pixel);
        addbet.setBounds(900, 600, 100, 50);
        System.out.println("add button should be seen");
        addbet.addActionListener(e -> {
            System.out.println("pridava");
            if (blackJack.players[blackJack.currentPlayerToBet].money > 0) {
                blackJack.players[blackJack.currentPlayerToBet].money -= 50;
                blackJack.players[blackJack.currentPlayerToBet].bet += 50;
                blackJack.changebetlabeltext();
            }
        });
        mainpanel.add(addbet);
        return addbet;
    }

    /**
     * Creates and returns the "Less Bet" button.
     *
     * @param pixel     The font to use.
     * @param mainpanel The panel to which the button will be added.
     * @param blackJack The BlackJack instance for accessing game state.
     * @return The configured "Less Bet" button.
     */
    public JButton setuplessbutton(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton lessbet = new JButton();
        ImageIcon icon = new ImageIcon("src/Images/BlackJack/minus.png");
        lessbet.setIcon(icon);
        lessbet.setContentAreaFilled(false);
        lessbet.setFocusPainted(false);
        lessbet.setBorderPainted(false);
        lessbet.addMouseListener(new SecHovering(2, null));
        lessbet.setHorizontalAlignment(SwingConstants.CENTER);
        lessbet.setVerticalAlignment(SwingConstants.CENTER);
        lessbet.setFont(pixel);
        lessbet.setBounds(200, 600, 100, 50);
        lessbet.addActionListener(e -> {
            System.out.println("ubira");
            if (blackJack.players[blackJack.currentPlayerToBet].bet > 0) {
                blackJack.players[blackJack.currentPlayerToBet].money += 50;
                blackJack.players[blackJack.currentPlayerToBet].bet -= 50;
                blackJack.changebetlabeltext();
            }
        });
        mainpanel.add(lessbet);
        return lessbet;
    }

    /**
     * Creates and returns an array of labels displaying player money.
     *
     * @param players      Array of PlayerState objects.
     * @param totalplayers Total number of players.
     * @param mainpanel    The panel to which the labels will be added.
     * @return Array of JLabels showing each player's money.
     */
    public JLabel[] monetlabels(PlayerState[] players, int totalplayers, JPanel mainpanel) {
        JLabel[] labels = new JLabel[3];
        //chat idea
        int[] xPositions = {100, 500, 900};
        int pos1 = 100, pos2 = 500, pos3 = 900;
        int yPosition = 555;
        //end of idea
        switch (totalplayers) {
            case 1 -> {
                xPositions[0] = pos2;
                xPositions[1] = pos1;
                xPositions[2] = pos3;
            }
            case 2 -> {
                xPositions[0] = pos1;
                xPositions[1] = pos3;
                xPositions[2] = pos2;
            }
            case 3 -> {
                xPositions[0] = pos1;
                xPositions[1] = pos2;
                xPositions[2] = pos3;
            }
        }

        for (int i = 0; i < totalplayers; i++) {
            JLabel label = new JLabel("Money: $" + players[i].money);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            label.setForeground(Color.YELLOW);
            label.setBounds(xPositions[i], yPosition, 200, 50);
            mainpanel.add(label);
            labels[i] = label;
        }
        return labels;
    }

    /**
     * Sets up and returns the label used to show current bet amount.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @return Configured JLabel.
     */
    public JLabel setupbetlabel(Font pixel, JPanel mainpanel) {
        JLabel betlabel = new JLabel("DEF");
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setFont(pixel);
        betlabel.setBounds(440, 600, 300, 100);
        mainpanel.add(betlabel);
        return betlabel;
    }

    /**
     * Creates and returns the BET button that finalizes a player's bet.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @param blackJack BlackJack game instance.
     * @return Configured JButton.
     */
    public JButton setupbetbutton(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton betbutton = new JButton("BET");
        betbutton.setFont(pixel);
        betbutton.setBounds(500, 700, 200, 50);
        betbutton.addActionListener(e -> {
            if (blackJack.currentPlayerToBet < blackJack.totalPlayers) {
                blackJack.players[blackJack.currentPlayerToBet].hasBet = true;
                blackJack.players[blackJack.currentPlayerToBet].phase++;
                System.out.println("Player " + (blackJack.currentPlayerToBet + 1) + " has bet.");
                blackJack.currentPlayerToBet++;

                if (blackJack.currentPlayerToBet == blackJack.totalPlayers) {
                    blackJack.cardDealing.dealInitialCards(true, blackJack);
                } else {
                    blackJack.buttonSetup.changeinfolabel(blackJack.showinfoLabel,
                            "Player " + (blackJack.currentPlayerToBet + 1) + " is betting", blackJack);
                }
            }
            blackJack.changebetlabeltext();
            blackJack.updateability();
            blackJack.cardDealing.whowins(blackJack);
            blackJack.updatemoneylabels();
        });
        return betbutton;
    }

    /**
     * Creates and returns the HIT button used during play.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @param blackJack BlackJack game instance.
     * @return Configured JButton.
     */
    public JButton setuphitbuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton hitbutton = setuphitbutton(pixel, mainpanel);
        hitbutton.setVisible(false);
        hitbutton.addActionListener(e -> {
            if (blackJack.playertochoose < blackJack.totalPlayers &&
                    !blackJack.players[blackJack.playertochoose].hashitstay) {
                blackJack.players[blackJack.playertochoose].hit = true;
                blackJack.players[blackJack.playertochoose].stay = false;
                blackJack.players[blackJack.playertochoose].hashitstay = true;

                System.out.println("Player " + (blackJack.playertochoose + 1) + " chose HIT");
                blackJack.playertochoose++;

                if (blackJack.playertochoose == blackJack.totalPlayers) {
                    blackJack.cardDealing.dealInitialCards(false, blackJack);
                } else {
                    blackJack.changetext("Player " + (blackJack.playertochoose + 1) + " is playing");
                }
            }
            blackJack.updateability();
            blackJack.cardDealing.whowins(blackJack);
            blackJack.updatemoneylabels();
        });
        return hitbutton;
    }

    /**
     * Creates and returns the STAY button used during play.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @param blackJack BlackJack game instance.
     * @return Configured JButton.
     */
    public JButton setupstaybuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton staybutton = setupstaybutton(pixel, mainpanel);
        staybutton.setVisible(false);
        staybutton.addActionListener(e -> {
            if (blackJack.playertochoose < blackJack.totalPlayers &&
                    !blackJack.players[blackJack.playertochoose].hashitstay) {
                blackJack.players[blackJack.playertochoose].hit = false;
                blackJack.players[blackJack.playertochoose].stay = true;
                blackJack.players[blackJack.playertochoose].hashitstay = true;

                System.out.println("Player " + (blackJack.playertochoose + 1) + " chose STAY");
                blackJack.playertochoose++;

                if (blackJack.playertochoose == blackJack.totalPlayers) {
                    blackJack.cardDealing.dealInitialCards(false, blackJack);
                } else {
                    blackJack.changetext("Player " + (blackJack.playertochoose + 1) + " is playing");
                }
            }
            blackJack.updateability();
            blackJack.cardDealing.whowins(blackJack);
            blackJack.updatemoneylabels();
        });
        return staybutton;
    }

    /**
     * Creates and returns the CONTINUE button to start a new round.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @param blackJack BlackJack game instance.
     * @return Configured JButton.
     */
    public JButton setupcontinuebuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton continuebutton = new JButton("CONTINUE");
        continuebutton.setFont(pixel);
        continuebutton.setHorizontalAlignment(SwingConstants.CENTER);
        continuebutton.setVerticalAlignment(SwingConstants.CENTER);
        continuebutton.setBounds(400, 600, 400, 100);
        continuebutton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            blackJack.dispose();
            new BlackJack(false, "BlackJackPck.BlackJack");
        }));
        continuebutton.setVisible(false);
        mainpanel.add(continuebutton);
        return continuebutton;
    }

    /**
     * Creates and returns the game info label.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @return Configured JLabel.
     */
    public JLabel setupinfolabel(Font pixel, JPanel mainpanel) {
        JLabel infolabel = new JLabel("INFO");
        infolabel.setHorizontalAlignment(SwingConstants.CENTER);
        infolabel.setVerticalAlignment(SwingConstants.CENTER);
        infolabel.setFont(pixel);
        infolabel.setOpaque(false);
        infolabel.setBounds(300, 220, 600, 100);
        mainpanel.add(infolabel);
        return infolabel;
    }

    /**
     * Changes the text of the info label.
     *
     * @param infolabel Label to modify.
     * @param text      New text.
     * @param blackjack BlackJack instance for repainting.
     */
    public void changeinfolabel(JLabel infolabel, String text, BlackJack blackjack) {
        infolabel.setText(text);
        blackjack.repaint();
    }

    /**
     * Creates a base HIT button without logic.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @return Configured JButton.
     */
    public JButton setuphitbutton(Font pixel, JPanel mainpanel) {
        JButton hitbutton = new JButton("HIT");
        hitbutton.setHorizontalAlignment(SwingConstants.CENTER);
        hitbutton.setVerticalAlignment(SwingConstants.CENTER);
        hitbutton.setFont(pixel);
        hitbutton.setOpaque(false);
        hitbutton.setBounds(350, 600, 200, 100);
        mainpanel.add(hitbutton);
        return hitbutton;
    }

    /**
     * Creates a base STAY button without logic.
     *
     * @param pixel     Font to apply.
     * @param mainpanel Panel to attach to.
     * @return Configured JButton.
     */
    public JButton setupstaybutton(Font pixel, JPanel mainpanel) {
        JButton staybutton = new JButton("STAY");
        staybutton.setHorizontalAlignment(SwingConstants.CENTER);
        staybutton.setVerticalAlignment(SwingConstants.CENTER);
        staybutton.setFont(pixel);
        staybutton.setOpaque(false);
        staybutton.setBounds(650, 600, 200, 100);
        mainpanel.add(staybutton);
        return staybutton;
    }

    /**
     * Sets up the exit button to return to the main menu.
     *
     * @param pixel     Font to apply.
     * @param blackJack The current BlackJack game instance.
     * @return Configured EXIT JButton.
     */
    public JButton setupexitbutton(Font pixel, BlackJack blackJack) {
        JButton exitbutton = new JButton("Exit");
        exitbutton.setFont(pixel);
        exitbutton.setBounds(1050, 0, 150, 100);
        exitbutton.addActionListener(e -> {
            new MainMenu();
            blackJack.dispose();
        });
        return exitbutton;
    }
}
