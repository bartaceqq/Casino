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

    public JLabel setupbetlabel(Font pixel, JPanel mainpanel) {
        JLabel betlabel = new JLabel("DEF");
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setFont(pixel);
        betlabel.setBounds(440, 600, 300, 100);
        mainpanel.add(betlabel);
        return betlabel;
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
