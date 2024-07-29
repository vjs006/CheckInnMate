package screencomponents.admincomponents;

import database.MongoModifier;
import hotelcomponents.rooms.Room;
import layouts.WrapLayout;
import screencomponents.RoomPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RemoveRoomPanel extends JPanel{
    private final MongoModifier mongo = new MongoModifier();

    private Font roomFont = new Font("Times New Roman", Font.PLAIN, 25);
    private JButton removeButton, detailsButton;
    private JLabel imageLabel, capacityLabel, areaLabel;
    private JPanel gapPanel1, gapPanel2, gapPanel3;

    public RemoveRoomPanel(Room room, ActionListener action) {
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 5));
        //setPreferredSize(new Dimension(0, 400));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(-1,-1, 1, -1);
        c.anchor = GridBagConstraints.CENTER;

        System.out.println(room.getType());
        Image img = new ImageIcon(RoomPanel.class.getResource("/RoomImages/" + room.getType() + ".jpg")).getImage();
        add(imageLabel = new JLabel(new ImageIcon(img.getScaledInstance(400, 200, Image.SCALE_SMOOTH))), c);

        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(-1,-1, 0, -1);

        add(gapPanel1 = new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
        }}, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(-1, 5, 0, -1);

        JLabel typeLabel = new JLabel("Room: " + room.getClass().getSimpleName());
        typeLabel.setForeground(Color.black);
        typeLabel.setFont(roomFont);
        typeLabel.setHorizontalAlignment(JLabel.LEFT);
        typeLabel.setVerticalAlignment(JLabel.CENTER);

        add(typeLabel, c);

        c.gridy = 3;
        c.insets = new Insets(-1, -1, 0, -1);

        add(new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
        }}, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(-1, 5, 0, -1);

        JLabel roomNoLabel = new JLabel("Room No: " + room.getRoomId());
        roomNoLabel.setForeground(Color.black);
        roomNoLabel.setFont(roomFont);
        roomNoLabel.setHorizontalAlignment(JLabel.LEFT);
        roomNoLabel.setVerticalAlignment(JLabel.CENTER);
        //typeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black);
        add(roomNoLabel, c);

        c.gridy = 5;
        c.insets = new Insets(-1, -1, 0, -1);

        add(new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
        }}, c);

        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(-1, 5, 0, -1);

        JLabel roomCostLabel = new JLabel("Cost Per Night: \u20B9" + (int)room.getCost());
        roomCostLabel.setForeground(Color.black);
        roomCostLabel.setFont(roomFont);
        roomCostLabel.setHorizontalAlignment(JLabel.LEFT);
        roomCostLabel.setVerticalAlignment(JLabel.CENTER);
        //typeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black);
        add(roomCostLabel, c);

        c.gridy = 7;
        c.insets = new Insets(-1, -1, 0, -1);

        add(new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
        }}, c);

        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(-1, 5, 0, -1);

        capacityLabel = new JLabel("Capacity: " + room.getCapacity());
        capacityLabel.setForeground(Color.black);
        capacityLabel.setFont(roomFont);
        capacityLabel.setHorizontalAlignment(JLabel.LEFT);
        capacityLabel.setVerticalAlignment(JLabel.CENTER);
        capacityLabel.setVisible(false);
        //typeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black);
        add(capacityLabel, c);

        c.gridy = 9;
        c.insets = new Insets(-1, -1, 0, -1);

        add(gapPanel2 = new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
            setVisible(false);
        }}, c);

        c.gridx = 0;
        c.gridy = 10;
        c.insets = new Insets(-1, 5, 0, -1);

        areaLabel = new JLabel("Area: " + room.getArea() + "m\u00B2");
        areaLabel.setForeground(Color.black);
        areaLabel.setFont(roomFont);
        areaLabel.setHorizontalAlignment(JLabel.LEFT);
        areaLabel.setVerticalAlignment(JLabel.CENTER);
        areaLabel.setVisible(false);
        //typeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black);
        add(areaLabel, c);

        c.gridy = 11;
        c.insets = new Insets(-1, -1, 0, -1);

        add(gapPanel3 = new JPanel(){{
            setPreferredSize(new Dimension(400, 4));
            setBackground(Color.black);
            setVisible(false);
        }}, c);


        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 1;
        c.insets = new Insets(-1, -1, -1, -1);
        c.fill = GridBagConstraints.BOTH;

        removeButton = new JButton("REMOVE");
        removeButton.setUI(new BasicButtonUI());
        removeButton.setBackground(Color.white);
        removeButton.setForeground(Color.black);
        //removeButton.setBorderPainted(false);
        removeButton.setFocusPainted(false);
        removeButton.setFont(roomFont);
        removeButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.black));
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setCursor(Cursor.getDefaultCursor());
            }
        });
        removeButton.addActionListener(action);
        add(removeButton, c);

        c.gridx = 1;
        c.gridy = 12;
        c.gridwidth = 1;
        c.insets = new Insets(-1, -1, -1, -1);
        c.fill = GridBagConstraints.BOTH;

        detailsButton = new JButton("GET DETAILS");
        detailsButton.setUI(new BasicButtonUI());
        detailsButton.setBackground(Color.white);
        detailsButton.setForeground(Color.black);
        detailsButton.setBorderPainted(false);
        detailsButton.setFocusPainted(false);
        detailsButton.setFont(roomFont);
        detailsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                detailsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                detailsButton.setCursor(Cursor.getDefaultCursor());
            }
        });
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("GET DETAILS")) {
                    imageLabel.setVisible(false);
                    capacityLabel.setVisible(true);
                    areaLabel.setVisible(true);
                    gapPanel1.setVisible(false);
                    gapPanel2.setVisible(true);
                    gapPanel3.setVisible(true);
                    ((JButton)e.getSource()).setText("CLOSE");
                }
                else {
                    imageLabel.setVisible(true);
                    capacityLabel.setVisible(false);
                    areaLabel.setVisible(false);
                    gapPanel1.setVisible(true);
                    gapPanel2.setVisible(false);
                    gapPanel3.setVisible(false);
                    ((JButton)e.getSource()).setText("GET DETAILS");
                }
            }
        });

        add(detailsButton, c);
        //setPreferredSize(new Dimension(500, 0));

    }
}
