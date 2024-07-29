package screencomponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppDefaultButton extends JButton {
    private String originalText;
    public AppDefaultButton(String text) {
        super("\n " + text + " \n");
        originalText = text;
        setUI(new BasicButtonUI());
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Times New Roman", Font.PLAIN, 30));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.black, 4));
        addMouseListener(new MouseListener(this));
    }
    public AppDefaultButton() {
        setUI(new BasicButtonUI());
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Times New Roman", Font.PLAIN, 30));
        setFocusPainted(false);
        addMouseListener(new MouseListener(this));
    }

    public String getOriginalText() {
        return originalText;
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
