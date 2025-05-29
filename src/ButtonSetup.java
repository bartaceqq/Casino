import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonSetup {

    public JButton setupaddbutton(Font pixel, JPanel mainpanel,BlackJack blackJack) {
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
        addbet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pridava");
                if (blackJack.players[blackJack.currentPlayerToBet].money>0){
                    blackJack.players[blackJack.currentPlayerToBet].money-=50;
                    blackJack.players[blackJack.currentPlayerToBet].bet+=50;

                   blackJack.changebetlabeltext();
                }
            }
        });
        mainpanel.add(addbet);
        return addbet;
    }

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
        lessbet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ubira");
                if (blackJack.players[blackJack.currentPlayerToBet].bet>0){
                    blackJack.players[blackJack.currentPlayerToBet].money+=50;
                    blackJack.players[blackJack.currentPlayerToBet].bet-=50;
                    blackJack.changebetlabeltext();
                }
            }
        });
        mainpanel.add(lessbet);
        return lessbet;
    }
    public JLabel[] monetlabels ( PlayerState[] players, int totalplayers, JPanel mainpanel) {
        JLabel[] labels = new JLabel[3];
        int[] xPositions = {100, 500, 900}; // You can adjust these positions
        int pos1 = 100;
        int pos2 = 500;
        int pos3 = 900;
        int yPosition = 555;
switch (totalplayers){
    case 1:
        xPositions[0] = pos2;
        xPositions[1] = pos1;
        xPositions[2] = pos3;
        break;
        case 2:
            xPositions[0] = pos1;
            xPositions[1] = pos3;
            xPositions[2] = pos2;
            break;
            case 3:
                xPositions[0] = pos1;
                xPositions[1] = pos2;
                xPositions[2] = pos3;
                break;
}
        for (int i = 0; i < totalplayers; i++) {
            JLabel label = new JLabel("Money: $" + players[i].money);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            label.setForeground(Color.YELLOW);
            label.setBounds(xPositions[i], yPosition, 200, 50);
            mainpanel.add(label);
            labels[i] = label; // ✅ Save reference so we can update later
        }
        return labels;
    }


    public JLabel setupbetlabel(Font pixel, JPanel mainpanel) {
        JLabel betlabel = new JLabel("DEF");
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setFont(pixel);
        betlabel.setBounds(440, 600, 300, 100);
        mainpanel.add(betlabel);
        return betlabel;
    }
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
                    blackJack.dealInitialCards(true);
                } else {
                    blackJack.buttonSetup.changeinfolabel(blackJack.showinfoLabel, "Player " + (blackJack.currentPlayerToBet + 1) + " is betting", blackJack);
                }
            }
            blackJack.changebetlabeltext();
            blackJack.updateability();
            blackJack.whowins();
            blackJack.updatemoneylabels();
        });
        return betbutton;
    }
    public JButton setuphitbuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton hitbutton = new JButton("BET");
        hitbutton = setuphitbutton(pixel, mainpanel);
        hitbutton.setVisible(false);
        hitbutton.addActionListener(e -> {
            if (blackJack.playertochoose < blackJack.totalPlayers && !blackJack.players[blackJack.playertochoose].hashitstay) {
                blackJack.players[blackJack.playertochoose].hit = true;
                blackJack.players[blackJack.playertochoose].stay = false;
                blackJack.players[blackJack.playertochoose].hashitstay = true;

                System.out.println("Player " + (blackJack.playertochoose + 1) + " chose HIT");

                blackJack.playertochoose++;
                if (blackJack.playertochoose == blackJack.totalPlayers) {
                    blackJack.dealInitialCards(false);
                } else {
                    blackJack.changetext("Player " + (blackJack.playertochoose + 1) + " is playing");
                }
            }
            blackJack.updateability();
            blackJack.whowins();
            blackJack.updatemoneylabels();
        });
        return hitbutton;
    }

    public JButton setupstaybuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton staybutton = new JButton("BET");
        staybutton = setupstaybutton(pixel, mainpanel);
        staybutton.setVisible(false);
        staybutton.addActionListener(e -> {
            if (blackJack.playertochoose < blackJack.totalPlayers && !blackJack.players[blackJack.playertochoose].hashitstay) {
                blackJack.players[blackJack.playertochoose].hit = false;
                blackJack.players[blackJack.playertochoose].stay = true;
                blackJack.players[blackJack.playertochoose].hashitstay = true;

                System.out.println("Player " + (blackJack.playertochoose + 1) + " chose STAY");

                blackJack.playertochoose++;
                if (blackJack.playertochoose == blackJack.totalPlayers) {
                    blackJack.dealInitialCards(false);
                } else {
                    blackJack.changetext("Player " + (blackJack.playertochoose + 1) + " is playing");
                }
            }
            blackJack.updateability();
            blackJack.whowins();
            blackJack.updatemoneylabels();

        });
        return staybutton;
    }
    public JButton setupcontinuebuttonek(Font pixel, JPanel mainpanel, BlackJack blackJack) {
        JButton continuebutton = new JButton("CONTINUE");
        continuebutton.setFont(pixel);
        continuebutton.setHorizontalAlignment(SwingConstants.CENTER);
        continuebutton.setVerticalAlignment(SwingConstants.CENTER);
        continuebutton.setBounds(400, 600, 400, 100);
        continuebutton.addActionListener(e -> {SwingUtilities.invokeLater(() -> {
            blackJack.dispose(); // Close current window
            new BlackJack(false, "BlackJack"); // Open new one
        });});
        continuebutton.setVisible(false);
        mainpanel.add(continuebutton);
        return continuebutton;
    }
    public JLabel setupinfolabel(Font pixel, JPanel mainpanel) {
        JLabel infolabel = new JLabel("INFO");
        infolabel.setHorizontalAlignment(SwingConstants.CENTER);
        infolabel.setVerticalAlignment(SwingConstants.CENTER);
        infolabel.setFont(pixel);
        infolabel.setOpaque(false);
        infolabel.setBounds(300, 250, 600, 100);
        mainpanel.add(infolabel);
        return infolabel;
    }

    public void changeinfolabel(JLabel infolabel, String text, BlackJack blackjack) {
        infolabel.setText(text);
        blackjack.repaint();
    }
    public JButton setuphitbutton( Font pixel, JPanel mainpanel) {
       JButton hitbutton = new JButton("HIT");
        hitbutton.setHorizontalAlignment(SwingConstants.CENTER);
        hitbutton.setVerticalAlignment(SwingConstants.CENTER);
        hitbutton.setFont(pixel);
        hitbutton.setOpaque(false);
        hitbutton.setBounds(350, 600, 200, 100);
        mainpanel.add(hitbutton);
        return hitbutton;
    }
    public JButton setupstaybutton( Font pixel, JPanel mainpanel) {
        JButton staybutton = new JButton("STAY");
        staybutton.setHorizontalAlignment(SwingConstants.CENTER);
        staybutton.setVerticalAlignment(SwingConstants.CENTER);
        staybutton.setFont(pixel);
        staybutton.setOpaque(false);
        staybutton.setBounds(650, 600, 200, 100);
        mainpanel.add(staybutton);
        return staybutton;
    }
}
