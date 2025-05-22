import javax.swing.*;
import java.awt.*;

public class ButtonSetup {
    public void setupaddbutton(JButton addbet, Font pixel, JPanel mainpanel) {
        addbet = new JButton("");
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
        mainpanel.add(addbet);
    }
    public void setuplessbutton(JButton lessbet, Font pixel, JPanel mainpanel){
        lessbet = new JButton("");
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
        mainpanel.add(lessbet);
    }
    public void addbetbutton(JButton betbutton, Font pixel, JPanel mainpanel){
        betbutton = new JButton("BET");
        betbutton.setHorizontalAlignment(SwingConstants.CENTER);
        betbutton.setVerticalAlignment(SwingConstants.CENTER);
        betbutton.setFont(pixel);
        betbutton.setBounds(550, 700, 200, 50);
        mainpanel.add(betbutton);
    }
    public void setupbetlabel(JLabel betlabel, Font pixel, JPanel mainpanel){
        betlabel = new JLabel("1000");
        betlabel.setHorizontalAlignment(SwingConstants.CENTER);
        betlabel.setVerticalAlignment(SwingConstants.CENTER);
        betlabel.setFont(pixel);
        betlabel.setBounds(440, 600, 300, 100);
        mainpanel.add(betlabel);
    }
}
