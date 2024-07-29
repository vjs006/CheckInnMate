package screencomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private JTextPane pleaseLoginText;
    private JFrame window;
    private JPanel screenPanel;
    private JPanel navPanel;
    private JPanel searchPanel;
    private JPanel loginPagePanel;

    private SignUpPanel signUpPanel;


    private Font hugeFont = new Font("Times New Roman", Font.PLAIN, 90);
    private Font largeFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font mediumFont = new Font("Times New Roman", Font.PLAIN, 45);
    private Font smallFont = new Font("Times New Roman", Font.PLAIN, 30);
    private Font littleFont = new Font("Times New Roman", Font.PLAIN, 24);
    private Font vsmallFont = new Font("Times New Roman", Font.PLAIN, 20);
    private Font tinyFont = new Font("Times New Roman", Font.PLAIN, 15);

    public Main() {
        window = new JFrame();
        window.setSize(1200, 800);
        window.setTitle("CheckInnMate");
        //window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.white);
        window.add(new ScreenPanel(), BorderLayout.CENTER);
        window.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
