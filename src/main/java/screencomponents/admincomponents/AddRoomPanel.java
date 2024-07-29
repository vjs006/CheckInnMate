package screencomponents.admincomponents;

import accessors.Guest;
import database.MongoModifier;
import hotelcomponents.rooms.Room;
import hotelexceptions.PreexisistingRoomException;
import org.bson.Document;
import screencomponents.constraints.DropDownPanel;
import stringoperations.StringModifier;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

public class AddRoomPanel extends JPanel{
    protected JTextField roomIDField;
    protected JComboBox<Integer> capacityDropDown;

    protected JTextField costField;
    protected JTextField areaField;
    protected JComboBox<String> roomTypeDropDown;
    protected JLabel loginTitle;
    protected JLabel errorLabel;
    protected JButton enterButton;

    protected GridBagConstraints c = new GridBagConstraints();
    public AddRoomPanel(){
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

        loginTitle = new JLabel("ADD ROOM");
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
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.EAST;

        JLabel usernameText = new JLabel("RoomID :");
        usernameText.setForeground(Color.black);
        usernameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(usernameText, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.WEST;

        roomIDField = new JTextField(4);
        roomIDField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //roomIDField.addActionListener(new CredentialsHandler());
        add(roomIDField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.EAST;

        JLabel passwordText = new JLabel("Capacity :");
        passwordText.setForeground(Color.black);
        passwordText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(passwordText, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.WEST;

        capacityDropDown = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        add(capacityDropDown, c);

        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.EAST;

        JLabel costLabel = new JLabel("Cost :");
        costLabel.setForeground(Color.black);
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(costLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.WEST;

        costField = new JTextField(4);
        costField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //capacityField.addActionListener(new CredentialsHandler());
        add(costField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.EAST;

        JLabel areaLabel = new JLabel("Area :");
        areaLabel.setForeground(Color.black);
        areaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(areaLabel, c);

        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.WEST;

        areaField = new JTextField(4);
        areaField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //capacityField.addActionListener(new CredentialsHandler());
        add(areaField, c);


        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.EAST;

        JLabel roomTypeLabel = new JLabel("Room Type :");
        roomTypeLabel.setForeground(Color.black);
        roomTypeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(roomTypeLabel, c);

        c.gridx = 1;
        c.gridy = 5;
        c.insets = new Insets(15, 15, 0, 15);
        c.anchor = GridBagConstraints.WEST;

        roomTypeDropDown = new JComboBox<>(new String[]{"Single Room", "Deluxe Room", "Double Room", "Executive Room", "Apartment Room", "Cabana Room", "King Room", "Queen Room"});
        add(roomTypeDropDown, c);


        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.insets = new Insets(22, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;

        enterButton = new JButton("ENTER");
        enterButton.setUI(new BasicButtonUI());
        enterButton.setForeground(Color.black);
        enterButton.setBackground(Color.white);
        enterButton.setFocusPainted(false);

        enterButton.setFont((new Font("Times New Roman", Font.PLAIN, 20)));
        //enterButton.addActionListener(new CredentialsHandler());
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
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoModifier mongo = new MongoModifier();
                //Room(int roomId, int capacity, double cost, int area, Guest guest)
                int roomID = (int)Integer.parseInt(roomIDField.getText());
                int capacity = (int)capacityDropDown.getSelectedItem();
                double cost = (double)Double.parseDouble(costField.getText());
                int area = (int)Integer.parseInt(areaField.getText());
                String roomType = (String)roomTypeDropDown.getSelectedItem();
                Room room;
                try {
                    room = ((Room)Class.forName("hotelcomponents.rooms." + StringModifier.removeSpace(roomType)).getConstructor(int.class, int.class, double.class, int.class, Guest.class).newInstance(roomID, capacity, cost, area, null));
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    mongo.addRoom(room);
                    /*roomIDField.setText("");
                    capacityDropDown.setSelectedIndex(0);
                    costField.setText("");
                    areaField.setText("");
                    roomTypeDropDown.setSelectedIndex(0);*/
                    errorLabel.setVisible(true);
                } catch (PreexisistingRoomException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //rooms.add((Room)Class.forName("hotelcomponents." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, null));

        add(enterButton, c);

        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 20, 5, 20);
        c.anchor = GridBagConstraints.CENTER;

        errorLabel = new JLabel("Room Added Successfully!!");
        errorLabel.setForeground(Color.green);
        errorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        errorLabel.setVisible(false);
        add(errorLabel, c);
    }
}
