package screencomponents.pages;

import database.MongoModifier;
import feedbackcomponents.Feedback;
import hotelexceptions.GuestNotFoundException;
import screencomponents.AppDefaultButton;
import screencomponents.ScreenPanel;
import stringoperations.StringModifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeedbackPagePanel extends JPanel {

    private JComboBox<String> componentTypeBox;
    private JComboBox<Integer> ratingBox;
    private JTextArea feedbackArea;

    public FeedbackPagePanel() {
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 30, 0);

        add(new JLabel("Feedback subject: ") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 40));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setForeground(Color.black);
        }}, c);

        c.gridx = 1;

        componentTypeBox = new JComboBox<String>(new String[]{"General", "Room", "Game Room", "Gym", "Swimming Pool"});
        componentTypeBox.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((JLabel)componentTypeBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)componentTypeBox.getRenderer()).setVerticalAlignment(SwingConstants.CENTER);
        add(componentTypeBox, c);

        c.gridx = 0;
        c.gridy = 1;

        add(new JLabel("Rating: ") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 40));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setForeground(Color.black);
        }}, c);

        c.gridx = 1;

        ratingBox = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5});
        ratingBox.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((JLabel)ratingBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)ratingBox.getRenderer()).setVerticalAlignment(SwingConstants.CENTER);

        add(ratingBox, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;

        feedbackArea = new JTextArea("Type here");
        feedbackArea.setBackground(new Color(240, 240, 240));
        feedbackArea.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        feedbackArea.setPreferredSize(new Dimension(600, 400));
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setLineWrap(true);
        feedbackArea.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        feedbackArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                feedbackArea.setText("");
            }
        });

        add(feedbackArea, c);

        c.gridy = 3;

        JButton sendFeedbackButton = new AppDefaultButton("ENTER");
        sendFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoModifier mongo = new MongoModifier();
                try {
                    String username = ((ScreenPanel)FeedbackPagePanel.this.getParent()).getAssociatedUsername();
                    mongo.getGuest(username)
                            .giveFeedback(new Feedback(username, StringModifier.removeSpace((String)componentTypeBox.getSelectedItem()), (int)ratingBox.getSelectedItem(), feedbackArea.getText()));
                    feedbackArea.setText("");
                    ratingBox.setSelectedItem("1");
                    componentTypeBox.setSelectedItem(("General"));
                }
                catch (GuestNotFoundException guestNotFoundException) {
                    guestNotFoundException.printStackTrace();
                }
            }
        });

        add(sendFeedbackButton, c);
    }
}
