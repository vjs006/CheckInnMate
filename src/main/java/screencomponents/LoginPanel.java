package screencomponents;

import accessors.User;
import database.MongoModifier;
import hotelcomponents.rooms.Room;
import hotelexceptions.GuestNotFoundException;
import hotelexceptions.IllegalPasswordAccessException;
import hotelexceptions.PreexisistingRoomException;
import hotelexceptions.UserNotFoundException;
import payments.PDFCreator;
import screencomponents.admincomponents.AddRoomPagePanel;
import screencomponents.admincomponents.CheckFeedbackPagePanel;
import screencomponents.admincomponents.RemoveRoomPagePanel;
import screencomponents.constraints.DropDownPanel;
import screencomponents.constraints.PriceBar;
import screencomponents.pages.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class LoginPanel extends JPanel {
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JLabel loginTitle;
    protected JLabel errorLabel;
    protected JButton enterButton;

    protected GridBagConstraints c = new GridBagConstraints();

    private static final String ADMIN_USERNAME = "ADMIN";
    private static final String ADMIN_PASSWORD = "adpass1";

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 5));
        setBackground(Color.white);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.insets = new Insets(20, 40, 0, 40);
        c.anchor = GridBagConstraints.CENTER;

        loginTitle = new JLabel("LOGIN");
        loginTitle.setBackground(Color.white);
        loginTitle.setForeground(Color.black);
        loginTitle.setHorizontalAlignment(JLabel.CENTER);
        loginTitle.setVerticalAlignment(JLabel.CENTER);
        loginTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        add(loginTitle, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel usernameText = new JLabel("Username: ");
        usernameText.setForeground(Color.black);
        usernameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(usernameText, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        usernameField.addActionListener(new CredentialsHandler());
        add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel passwordText = new JLabel("Password: ");
        passwordText.setForeground(Color.black);
        passwordText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(passwordText, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        passwordField.addActionListener(new CredentialsHandler());
        add(passwordField, c);

        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;

        enterButton = new JButton("ENTER");
        enterButton.setUI(new BasicButtonUI());
        enterButton.setForeground(Color.black);
        enterButton.setBackground(Color.white);
        enterButton.setFocusPainted(false);

        enterButton.setFont((new Font("Times New Roman", Font.PLAIN, 20)));
        enterButton.addActionListener(new CredentialsHandler());
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        add(enterButton, c);

        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 20, 5, 20);
        c.anchor = GridBagConstraints.CENTER;

        errorLabel = new JLabel("Username or Password is Incorrect");
        errorLabel.setForeground(Color.red);
        errorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        errorLabel.setVisible(false);
        add(errorLabel, c);

    }
    private class CredentialsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            usernameField.setText("");
            passwordField.setText("");

            MongoModifier mongo = new MongoModifier();
            ScreenPanel screenPanel = (ScreenPanel) getParent().getParent();

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                screenPanel.remove(1);
                screenPanel.add(new AddRoomPagePanel());
                screenPanel.revalidate();
                screenPanel.repaint();

                ((NavPanel)screenPanel.getComponent(0)).removeAll();
                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Add Rooms") {{
                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            screenPanel.remove(1);
                            screenPanel.add(new AddRoomPagePanel());
                            screenPanel.revalidate();
                            screenPanel.repaint();
                        }
                    });
                }});

                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Remove Rooms") {{
                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            screenPanel.remove(1);
                            screenPanel.add(new JScrollPane(new RemoveRoomPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                getVerticalScrollBar().setUnitIncrement(10);
                            }}, BorderLayout.CENTER);
                            screenPanel.revalidate();
                            screenPanel.repaint();
                        }
                    });
                }});

                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Check Feedback") {{
                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            screenPanel.remove(1);
                            screenPanel.add(new JScrollPane(new CheckFeedbackPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                getVerticalScrollBar().setUnitIncrement(10);
                            }}, BorderLayout.CENTER);
                            screenPanel.revalidate();
                            screenPanel.repaint();
                        }
                    });
                }});

                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Sign Out") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame window = (JFrame)SwingUtilities.getWindowAncestor(screenPanel);
                            window.getContentPane().removeAll();
                            window.add(new ScreenPanel(), BorderLayout.CENTER);
                            window.revalidate();
                            window.repaint();
                        }
                    });
                }});
                return;
            }
            for (User user : mongo.getAllUsers()) {
                if (user.getUsername().equals(username)) {
                    try {
                        if (mongo.getPassword(username, "awZYbHxLl4WykOhSiujjJm7U9VuEYx2X").equals(password)) {
                            try {
                                screenPanel.setAssociatedGuest(mongo.getGuest(username));
                                screenPanel.setAssociatedUsername(username);
                                screenPanel.setAssociatedUser(screenPanel.getAssociatedGuest());
                                screenPanel.remove(1);
                                screenPanel.add(new UtilizeAmenitiesPagePanel());
                                screenPanel.revalidate();
                                screenPanel.repaint();

                                ((NavPanel)screenPanel.getComponent(0)).removeAll();
                                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Utilize Amenities") {{
                                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                                    addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            screenPanel.remove(1);
                                            screenPanel.add(new UtilizeAmenitiesPagePanel());
                                            screenPanel.revalidate();
                                            screenPanel.repaint();
                                        }
                                    });
                                }});

                                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Give Feedback") {{
                                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                                    addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            screenPanel.remove(1);
                                            screenPanel.add(new FeedbackPagePanel());
                                            screenPanel.revalidate();
                                            screenPanel.repaint();
                                        }
                                    });
                                }});

                                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Check Out") {{
                                    addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (((JButton)e.getSource()).getText().equals("Check Out")) {
                                                ((JButton)e.getSource()).setText("Confirm?");
                                                ((JButton)e.getSource()).setBackground(Color.red);
                                            }
                                            else {
                                                PDFCreator pdf = new PDFCreator();
                                                try {
                                                    pdf.createPDF(screenPanel.getAssociatedGuest());
                                                }
                                                catch (FileNotFoundException fileNotFoundException) {
                                                    fileNotFoundException.printStackTrace();
                                                }
                                                MongoModifier mongo = new MongoModifier();
                                                mongo.removeBill(screenPanel.getAssociatedGuest());
                                                mongo.removeGuest(screenPanel.getAssociatedGuest());
                                                for (Room room : screenPanel.getAssociatedGuest().getRooms()) {
                                                    mongo.removeRoom(room);
                                                    try {
                                                        mongo.addRoom(room);
                                                    }
                                                    catch (PreexisistingRoomException preexisistingRoomException) {
                                                        preexisistingRoomException.printStackTrace();
                                                    }
                                                }
                                                System.exit(0);
                                            }
                                        }
                                    });
                                }});

                                ((NavPanel)screenPanel.getComponent(0)).add(new NavButton("Sign Out") {{
                                    addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            JFrame window = (JFrame)SwingUtilities.getWindowAncestor(screenPanel);
                                            window.getContentPane().removeAll();
                                            window.add(new ScreenPanel(), BorderLayout.CENTER);
                                            window.revalidate();
                                            window.repaint();
                                        }
                                    });
                                }});
                                return;
                            }
                            catch (GuestNotFoundException ex) {

                            }
                            screenPanel.remove(1);
                            ((JPanel)screenPanel.getComponent(0)).removeAll();
                            ((JPanel)screenPanel.getComponent(0)).add(new NavButton("Search") {{
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        //System.out.println(((JScrollPane)(((JComponent)(e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView());
                                        try {
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).reDisplayRooms();
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).revalidate();
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).repaint();
                                        }
                                        catch (ClassCastException ex) {
                                            ex.printStackTrace();
                                            screenPanel.remove(1);
                                            screenPanel.add(new JScrollPane(new SearchPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                                getVerticalScrollBar().setUnitIncrement(10);
                                            }}, BorderLayout.CENTER);
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).reDisplayRooms();
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).revalidate();
                                            ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).repaint();
                                        }
                                    }
                                });
                            }});
                            PriceBar pbl = new PriceBar(PriceBar.LOWER);
                            PriceBar pbh = new PriceBar(PriceBar.UPPER);
                            pbl.setAssociatedSlider(pbh.getCostSlider());
                            pbh.setAssociatedSlider(pbl.getCostSlider());
                            ((JPanel)screenPanel.getComponent(0)).add(pbl);
                            ((JPanel)screenPanel.getComponent(0)).add(pbh);
                            ((JPanel)screenPanel.getComponent(0)).add(new DropDownPanel<String>("Capacity", new String[]{"ALL", "1", "2", "3", "4", "5", "6"}));
                            ((JPanel)screenPanel.getComponent(0)).add(new DropDownPanel<String>("Room  ", new String[]{"ALL", "Single Room", "Deluxe Room", "Double Room", "Executive Room", "Apartment Room", "Cabana Room", "King Room", "Queen Room"}));
                            //System.out.println("HIIII");
                            screenPanel.add(new JScrollPane(new SearchPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                getVerticalScrollBar().setUnitIncrement(10);
                            }}, BorderLayout.CENTER);

                            ((JPanel)screenPanel.getComponent(0)).add(new NavButton("User Details") {{
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (((JButton)e.getSource()).getText().equals("User Details")) {
                                            screenPanel.remove(1);
                                            screenPanel.add(new UserDetailsPagePanel(username));
                                            ((JButton) e.getSource()).setText("Close Details");
                                        }
                                        else {
                                            screenPanel.remove(1);
                                            screenPanel.add(new JScrollPane(new SearchPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                                getVerticalScrollBar().setUnitIncrement(10);
                                            }}, BorderLayout.CENTER);
                                            ((JButton) e.getSource()).setText("User Details");
                                        }
                                    }
                                });
                            }});

                            ((JPanel)screenPanel.getComponent(0)).add(new NavButton("Confirm Booking") {{
                                setFont(new Font("Times New Roman", Font.PLAIN, 24));
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (((JButton)e.getSource()).getText().equals("Confirm Booking")) {
                                            HashSet<Room> rooms = ((SearchPagePanel) ((JScrollPane) (((JComponent) (e.getSource())).getParent().getParent()).getComponent(1)).getViewport().getView()).getBookedRooms();
                                            screenPanel.remove(1);
                                            screenPanel.add(new ConfirmBookingPanel(rooms));
                                            ((JButton) e.getSource()).setText("Close");
                                        }
                                        else {
                                            screenPanel.remove(1);
                                            screenPanel.add(new JScrollPane(new SearchPagePanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
                                                getVerticalScrollBar().setUnitIncrement(10);
                                            }}, BorderLayout.CENTER);
                                            ((JButton) e.getSource()).setText("Confirm Booking");
                                        }
                                    }
                                });
                            }});

                            ((JPanel)screenPanel.getComponent(0)).add(new NavButton("Sign Out") {{
                                setBorder(null);
                                addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JFrame window = (JFrame)SwingUtilities.getWindowAncestor(screenPanel);
                                        window.getContentPane().removeAll();
                                        window.add(new ScreenPanel(), BorderLayout.CENTER);
                                        window.revalidate();
                                        window.repaint();
                                    }
                                });
                            }});

                            screenPanel.revalidate();
                            screenPanel.repaint();
                            ((ScreenPanel)screenPanel).setAssociatedUsername(username);
                            screenPanel.setAssociatedUser(mongo.getUser(username));
                        }
                    }
                    catch (IllegalPasswordAccessException | UserNotFoundException ex) {
                        System.out.println(ex);

                    }
                }
                else {
                    errorLabel.setVisible(true);
                }
            }

        }
    }
}

//Funny issue with SearchPagePanel