package screencomponents.pages;

import accessors.User;
import database.MongoModifier;
import hotelexceptions.UserNotFoundException;
import screencomponents.ScreenPanel;

import javax.swing.*;
import java.awt.*;

public class UserDetailsPagePanel extends JPanel {

    public UserDetailsPagePanel(String username) {
        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setLayout(new GridBagLayout());
    }

    @Override
    public void addNotify() {
        super.addNotify();
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.white);
        userDetailsPanel.setLayout(new GridLayout(0, 1));
        userDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 7));
        User user = ((ScreenPanel)getParent()).getAssociatedUser();

        userDetailsPanel.add(new JLabel("Username: " + user.getUsername()) {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.PLAIN, 50));
            setHorizontalAlignment(JLabel.LEFT);
            setVerticalAlignment(JLabel.CENTER);
            setBorder(BorderFactory.createMatteBorder(0, 0, 7, 0, Color.black));
        }});
        userDetailsPanel.add(new JLabel("Name: " + user.getName()) {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.PLAIN, 50));
            setHorizontalAlignment(JLabel.LEFT);
            setVerticalAlignment(JLabel.CENTER);
            setBorder(BorderFactory.createMatteBorder(0, 0, 7, 0, Color.black));
        }});
        userDetailsPanel.add(new JLabel("Phone Number: " + user.getPhoneNumber()) {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.PLAIN, 50));
            setHorizontalAlignment(JLabel.LEFT);
            setVerticalAlignment(JLabel.CENTER);
            setBorder(BorderFactory.createMatteBorder(0, 0, 7, 0, Color.black));
        }});
        userDetailsPanel.add(new JLabel("Email Address: " + user.getEmailId()) {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.PLAIN, 50));
            setHorizontalAlignment(JLabel.LEFT);
            setVerticalAlignment(JLabel.CENTER);
        }});

        add(userDetailsPanel, new GridBagConstraints());
    }
}
