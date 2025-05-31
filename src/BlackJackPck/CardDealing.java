package BlackJackPck;

import MoneyLoaders.MoneyLoaderBlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles the logic for dealing cards in a game of Blackjack.
 * Manages both player and dealer card dealing, as well as round evaluation and reset.
 */
public class CardDealing {

    /**
     * Deals the initial cards to all players and the dealer.
     *
     * @param betting    True if this is the betting phase, false if it's during the hit/stay phase.
     * @param blackJack  The main BlackJack game object containing state and UI elements.
     */

    public void dealInitialCards(boolean betting, BlackJack blackJack) {
        blackJack.counter++;
        blackJack.playertochoose = 0;

        if (!blackJack.dealer.hasBet) {
            dealCardToDealer(blackJack);
            blackJack.dealer.hasBet = true;
        } else {
            blackJack.dealerround();
            if (blackJack.dealer.hit) {
                dealCardToDealer(blackJack);
            }
        }

        for (int i = 0; i < blackJack.players.length; i++) {
            //chat
            int rawRank = (int) (Math.random() * 13) + 1;
            int rank = (rawRank == 1) ? 11 : (rawRank >= 11) ? 10 : rawRank;
            int suit = (int) (Math.random() * 4) + 1;

            BufferedImage img = blackJack.cards[rank][suit];
            if (img == null) {
                System.out.println("error while loading card: [rank] = " + rank + ", suit = " + suit);
                continue;
            }

            int cardWidth = 128;
            int cardHeight = 128;
            int x = blackJack.players[i].position.x;
            int y = blackJack.players[i].position.y;

            JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));

            if (betting && !blackJack.players[i].hasCard && blackJack.players[i].hasBet) {
                label.setBounds(x, y, cardWidth, cardHeight);
                blackJack.players[i].hasCard = true;
                blackJack.players[i].value += rank;
                System.out.println("Dealt card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
            } else if (!betting && !blackJack.players[i].hassecondcard && blackJack.players[i].hashitstay && blackJack.players[i].hit) {
                int yOffset = (-30) * blackJack.counter;
                int xOffset = (-30) * blackJack.counter;
                label.setBounds(x + xOffset, y + yOffset, cardWidth, cardHeight);
                blackJack.players[i].hassecondcard = true;
                blackJack.players[i].value += rank;
                System.out.println("Dealt second card to player " + (i + 1) + ": Rank " + rank + ", Suit " + suit);
            } else {
                continue;
            }
            //end of chat
            blackJack.roundcounter++;
            blackJack.mainpanel.add(label);
            blackJack.mainpanel.repaint();
        }

        for (int i = 0; i < blackJack.players.length; i++) {
            System.out.println("Player " + (i + 1) + " has value: " + blackJack.players[i].value);
        }

        whowins(blackJack);
        blackJack.resetRound();
    }

    /**
     * Deals one card to the dealer and updates the dealer's value and GUI.
     *
     * @param blackJack The main BlackJack game object containing state and UI elements.
     */
    //this method i made by myself but i copy pasted some parts from dealInitalCard that were made form chat
    public void dealCardToDealer(BlackJack blackJack) {
        int rawRankd = (int) (Math.random() * 13) + 1;
        int rankd = (rawRankd == 1) ? 11 : (rawRankd >= 11) ? 10 : rawRankd;
        int suitd = (int) (Math.random() * 4) + 1;

        BufferedImage imgd = blackJack.cards[rankd][suitd];
        if (imgd == null) {
            System.out.println("error while loading card: [rank] = " + rankd + ", suit = " + suitd);
        }

        blackJack.dealer.value += rankd;
        int cardWidthd = 128;
        int cardHeightd = 128;
        int yOffsetd = (-20) * blackJack.roundcounterd;
        int xOffsetd = (-20) * blackJack.roundcounterd;
        int xd = blackJack.dealerPosition.x;
        int yd = blackJack.dealerPosition.y;

        JLabel labeld = new JLabel(new ImageIcon(imgd.getScaledInstance(cardWidthd, cardHeightd, Image.SCALE_SMOOTH)));
        labeld.setBounds(xd + xOffsetd, yd + yOffsetd, cardWidthd, cardHeightd);
        blackJack.mainpanel.add(labeld);
        blackJack.mainpanel.repaint();
        blackJack.roundcounterd++;
    }

    /**
     * Resets the round, hides buttons, and prepares for the next round.
     * Also saves money values for players.
     *
     * @param blackJack        The main BlackJack game object containing game state.
     * @param mainMoneyLoader  The object responsible for saving money data.
     */
    public void fullResetRound(BlackJack blackJack, MoneyLoaderBlackJack mainMoneyLoader) {
        //chat
        mainMoneyLoader.saveMoney(
                blackJack.players[0],
                blackJack.totalPlayers > 1 ? blackJack.players[1] : null,
                blackJack.totalPlayers > 2 ? blackJack.players[2] : null
        );
        //chat
        blackJack.updatemoneylabels();
        blackJack.hitbutton.setVisible(false);
        blackJack.staybutton.setVisible(false);
        blackJack.continuebutton.setVisible(true);
    }

    /**
     * Determines the result of the round based on dealer and player card values,
     * and applies money payouts accordingly.
     * Also handles saving player data and calling the round reset.
     *
     * @param blackJack The main BlackJack game object containing game state.
     */
    public void whowins(BlackJack blackJack) {
        boolean allDoneOrBusted = true;

        for (PlayerState playerstate : blackJack.players) {
            if (!playerstate.playerisdone && playerstate.value <= 21) {
                allDoneOrBusted = false;
                break;
            }
        }

        if (allDoneOrBusted) {
            while (blackJack.dealer.value < 17) {
                blackJack.cardDealing.dealCardToDealer(blackJack);
            }

            for (PlayerState player : blackJack.players) {
                if (player.value > 21) {
                    System.out.println("Player busted: no payout");
                } else if (blackJack.dealer.value > 21) {
                    player.money += player.bet * 2;
                } else if (player.value > blackJack.dealer.value) {
                    player.money += player.bet * 2;
                } else if (player.value == blackJack.dealer.value) {
                    player.money += player.bet;
                } else {
                    System.out.println("Dealer beats player: no payout");
                }
            }

            switch (blackJack.totalPlayers) {
                case 1:
                    blackJack.moneyloader.saveMoney(blackJack.players[0], null, null);
                    break;
                case 2:
                    blackJack.moneyloader.saveMoney(blackJack.players[0], blackJack.players[1], null);
                    break;
                case 3:
                    blackJack.moneyloader.saveMoney(blackJack.players[0], blackJack.players[1], blackJack.players[2]);
                    break;
            }

            System.out.println("Round finished. Resetting...");
            blackJack.cardDealing.fullResetRound(blackJack, blackJack.moneyloader);
            blackJack.mainpanel.repaint();
        }
    }
}
