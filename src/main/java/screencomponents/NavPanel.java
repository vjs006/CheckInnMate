package screencomponents;

import screencomponents.pages.LoginPagePanel;
import screencomponents.pages.SearchPagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavPanel extends JPanel {
    private NavButton searchPageButton;
    private NavButton userDetailsPageButton;
    private NavButton utilizeAmenitiesButton;
    private NavButton checkOutButton;
    private NavButton signOutButton;
    private NavButton signUpButton;
    private NavButton loginButton;
    private NavButton modifyRoomsPageButton;
    private JPanel screenPanel;

    private Font smallFont = new Font("Times New Roman", Font.PLAIN, 30);
    private Font littleFont = new Font("Times New Roman", Font.PLAIN, 24);

    public NavPanel(JPanel screenPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setBackground(Color.white);
        setPreferredSize(new Dimension(200, 133));
        setLayout(new GridLayout(8, 1));
        this.screenPanel = screenPanel;

        signUpButton = new NavButton("Sign Up");
        signUpButton.addActionListener(new NavButtonClicked());
        add(signUpButton);

        loginButton = new NavButton("Login");
        loginButton.addActionListener(new NavButtonClicked());
        //navPanel.add(loginButton);
    }

    private class NavButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (((NavButton)e.getSource()).getText()) {
                case "Sign Up" :
                    ((JComponent)screenPanel.getComponent(1)).getComponent(0).setVisible(false);
                    ((JComponent)screenPanel.getComponent(1)).getComponent(1).setVisible(true);
                    remove(signUpButton);
                    add(loginButton);
                    revalidate();
                    repaint();
                    break;
                case "Login" :
                    ((JComponent)screenPanel.getComponent(1)).getComponent(0).setVisible(true);
                    ((JComponent)screenPanel.getComponent(1)).getComponent(1).setVisible(false);
                    remove(loginButton);
                    add(signUpButton);
                    revalidate();
                    repaint();
                    break;
                default:
                    System.out.println("ILLEGAL OPTION");
                    break;
            }
        }
    }
}
