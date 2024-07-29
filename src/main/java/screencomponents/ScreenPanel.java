package screencomponents;

import accessors.Guest;
import accessors.User;
import screencomponents.pages.LoginPagePanel;

import javax.swing.*;
import java.awt.*;

public class ScreenPanel extends JPanel {
    private String associatedUsername;
    private Guest associatedGuest;
    private User associatedUser;

    public ScreenPanel() {
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setBackground(Color.white);
        setLayout(new BorderLayout());

        add(new NavPanel(this), BorderLayout.WEST);
        add(new LoginPagePanel(), BorderLayout.CENTER);
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedGuest(Guest associatedGuest) {
        this.associatedGuest = associatedGuest;
    }

    public Guest getAssociatedGuest() {
        return associatedGuest;
    }

    public User getAssociatedUser() {
        return associatedUser;
    }

    public void setAssociatedUser(User associatedUser) {
        this.associatedUser = associatedUser;
    }
}

