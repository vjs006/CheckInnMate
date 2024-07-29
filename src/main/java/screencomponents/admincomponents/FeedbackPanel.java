package screencomponents.admincomponents;

import feedbackcomponents.Feedback;
import stringoperations.StringModifier;

import javax.swing.*;
import java.awt.*;

public class FeedbackPanel extends JPanel{

    public FeedbackPanel(Feedback feedback) {
        GridBagConstraints c = new GridBagConstraints();

        setBackground(Color.white);
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(500, 300));
        setBorder(BorderFactory.createLineBorder(Color.black, 4));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 15, 0, 120);

        add(new JPanel() {{
            setBackground(Color.white);
            add(new JLabel("Username: " + feedback.getAssociatedUsername()) {{
                setFont(new Font("Times New Roman", Font.PLAIN, 32));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.black);
            }});
        }}, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 120, 0, 15);

        add(new JPanel() {{
            setBackground(Color.white);
            add(new JLabel("Date: " + feedback.getDate()) {{
                setFont(new Font("Times New Roman", Font.PLAIN, 32));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.black);
            }});
        }}, c);



        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 15, 0, 120);

        add(new JPanel() {{
            setBackground(Color.white);
            add(new JLabel("Topic: " + StringModifier.addSpace(feedback.getType())) {{
                setFont(new Font("Times New Roman", Font.PLAIN, 32));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.black);
            }});
        }}, c);



        c.gridx = 1;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 120, 0, 15);

        add(new JPanel() {{
            setBackground(Color.white);
            add(new JLabel("Rating : " + feedback.getRating() + "/5") {{
                setFont(new Font("Times New Roman", Font.PLAIN, 32));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.black);
            }});
        }}, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 20, 0, 15);

        add(new JTextArea("Comment:\n" + feedback.getFeedbackString()) {{
            setLineWrap(true);
            setWrapStyleWord(true);
            setBackground(Color.white);
            setForeground(Color.black);
            setEditable(false);
            setFont(new Font("Times New Roman", Font.PLAIN, 40));
        }}, c);
    }
}
