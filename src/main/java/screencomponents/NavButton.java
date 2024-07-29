package screencomponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavButton extends JButton {
    public NavButton (String content){
        setText(content);
        setUI(new BasicButtonUI());
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Times New Roman", Font.PLAIN, 30));
        setFocusPainted(false);
        setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.black));
        addMouseListener(new MouseListener(this));
    }
    private class MouseListener extends MouseAdapter {
        private JButton jb;
        MouseListener(JButton jb){
            this.jb = jb;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            jb.setCursor(Cursor.getDefaultCursor());
        }
    }


}
