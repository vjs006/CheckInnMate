package screencomponents;

import accessors.User;
import database.MongoModifier;
import hotelexceptions.IllegalPasswordAccessException;
import hotelexceptions.PreexisistingUserException;
import hotelexceptions.UserNotFoundException;
import screencomponents.pages.LoginPagePanel;
import screencomponents.pages.SearchPagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class SignUpPanel extends LoginPanel {
    private JPasswordField retypePasswordField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField emailField;

    private Pattern usernamePattern = Pattern.compile("^[\\w\\s]+$");
    private Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]+$");
    private Pattern emailPattern = Pattern.compile("^[\\w.+\\-]+@[\\w.+\\-]+$");
    private Pattern phoneNumberPattern = Pattern.compile("^\\+\\d{1,3}\\s\\d{7,15}$");
    public SignUpPanel() {
        loginTitle.setText("SIGN UP");

        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel retypePasswordLabel = new JLabel("Retype Password: ");
        retypePasswordLabel.setBackground(Color.white);
        retypePasswordLabel.setForeground(Color.black);
        retypePasswordLabel.setHorizontalAlignment(JLabel.CENTER);
        retypePasswordLabel.setVerticalAlignment(JLabel.CENTER);
        retypePasswordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(retypePasswordLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;
        
        retypePasswordField = new JPasswordField(10);
        retypePasswordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        retypePasswordField.addActionListener(new CredentialsHandler());
        add(retypePasswordField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel nameText = new JLabel("Full Name: ");
        nameText.setForeground(Color.black);
        nameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(nameText, c);

        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        nameField = new JTextField(10);
        nameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nameField.addActionListener(new CredentialsHandler());
        add(nameField, c);

        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel phoneNumberText = new JLabel("Phone Number: ");
        phoneNumberText.setForeground(Color.black);
        phoneNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(phoneNumberText, c);

        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        phoneNumberField = new JTextField(10);
        phoneNumberField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        phoneNumberField.addActionListener(new CredentialsHandler());
        add(phoneNumberField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        JLabel emailText = new JLabel("Email: ");
        emailText.setForeground(Color.black);
        emailText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(emailText, c);

        c.gridx = 1;
        c.gridy = 6;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;

        emailField = new JTextField(10);
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        emailField.addActionListener(new CredentialsHandler());
        add(emailField, c);

        //errorLabel.setText();
        enterButton.removeActionListener(enterButton.getActionListeners()[0]);
        enterButton.addActionListener(new CredentialsHandler());
        usernameField.addActionListener(new CredentialsHandler());
        passwordField.addActionListener(new CredentialsHandler());
        retypePasswordField.addActionListener(new CredentialsHandler());
    }
    private class CredentialsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            MongoModifier mongo = new MongoModifier();

            if (!usernamePattern.matcher(username).find()) {
                errorLabel.setText("Invalid username : Only use letters, digits, _ and spaces");
                errorLabel.setVisible(true);
            }
            else if (!password.equals(retypePassword)) {
                errorLabel.setText("Passwords do not match");
                errorLabel.setVisible(true);
            }
            else if (!namePattern.matcher(name).find()) {
                errorLabel.setText("Invalid Name : Only use letters and spaces");
                errorLabel.setVisible(true);
            }
            else if (!phoneNumberPattern.matcher(phoneNumber).find()) {
                errorLabel.setText("Invalid Phone Number : Please also add country code");
                errorLabel.setVisible(true);
            }
            else if (!emailPattern.matcher(email).find()) {
                errorLabel.setText("Invalid Email Format");
                errorLabel.setVisible(true);
            }
            else {
                try {
                    mongo.addUser(new User(username, name, phoneNumber, email), password);
                }
                catch (PreexisistingUserException ex) {
                    errorLabel.setText("Username already exists");
                    errorLabel.setVisible(true);
                    return;
                }
                JPanel screenPanel = (JPanel)getParent().getParent();
                ((JComponent)screenPanel.getComponent(1)).getComponent(0).setVisible(true);
                ((JComponent)screenPanel.getComponent(1)).getComponent(1).setVisible(false);
                screenPanel.removeAll();
                screenPanel.add(new NavPanel(screenPanel), BorderLayout.WEST);
                screenPanel.add(new LoginPagePanel(), BorderLayout.CENTER);
                screenPanel.revalidate();
                screenPanel.repaint();
            }
        }
    }
}
