package screencomponents.pages;

import screencomponents.LoginPanel;
import screencomponents.SignUpPanel;

import javax.swing.*;
import java.awt.*;

public class LoginPagePanel extends JPanel{
    private LoginPanel loginPanel;
    private SignUpPanel signUpPanel;

    public LoginPagePanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        loginPanel = new LoginPanel();
        add(loginPanel, new GridBagConstraints());

        signUpPanel = new SignUpPanel();
        signUpPanel.setVisible(false);
        add(signUpPanel, new GridBagConstraints());
    }
}
