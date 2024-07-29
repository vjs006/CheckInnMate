package screencomponents.pages;

import accessors.Guest;
import database.MongoModifier;
import hotelcomponents.rooms.Room;
import hotelexceptions.PreexisistingRoomException;
import hotelexceptions.UserNotFoundException;
import layouts.WrapLayout;
import screencomponents.NavButton;
import screencomponents.RoomPanel;
import screencomponents.ScreenPanel;
import stringoperations.StringModifier;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.HashSet;

public class ConfirmBookingPanel extends JPanel {
    private JPanel roomsPanel;
    public ConfirmBookingPanel(HashSet<Room> rooms) {
        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setLayout(new BorderLayout());

        add(new JPanel() {{
            setBackground(Color.white);
            add(new JLabel("WOULD YOU LIKE TO CONFIRM YOUR BOOKING?") {{
                setForeground(Color.black);
                setFont(new Font("Times New Roman", Font.BOLD, 35));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
            }});
        }}, BorderLayout.NORTH);


        add(new JScrollPane(roomsPanel = new JPanel() {{
            setBackground(Color.white);
            setLayout(new WrapLayout(FlowLayout.CENTER, 33, 33));
        }}, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {{
            getVerticalScrollBar().setUnitIncrement(10);
        }}, BorderLayout.CENTER);

        //setPreferredSize(new Dimension(500, 0));
        for (Room room : rooms) {
            roomsPanel.add((new RoomPanel(room, null) {{
                disableButtons();
            }}));
        }

        JComboBox<LocalDate> checkInDate = new JComboBox<>();
        ((JLabel)checkInDate.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        for (LocalDate date = LocalDate.now(); date.isBefore(LocalDate.now().plusDays(60)); date = date.plusDays(1)) {
            checkInDate.addItem(date);
        }

        JComboBox<LocalDate> checkOutDate = new JComboBox<>();
        ((JLabel)checkOutDate.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        for (LocalDate date = LocalDate.now(); date.isBefore(LocalDate.now().plusDays(60)); date = date.plusDays(1)) {
            checkOutDate.addItem(date);
        }
        checkOutDate.setSelectedItem(1);

        checkInDate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((LocalDate)checkInDate.getSelectedItem()).isAfter(((LocalDate)checkOutDate.getSelectedItem())))
                    checkInDate.setSelectedItem(((LocalDate)checkOutDate.getSelectedItem()).minusDays(1));
            }
        });

        checkOutDate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((LocalDate)checkOutDate.getSelectedItem()).isBefore(((LocalDate)checkInDate.getSelectedItem())))
                    checkOutDate.setSelectedItem(((LocalDate)checkInDate.getSelectedItem()).plusDays(1));
            }
        });

        JLabel checkInLabel = new JLabel("Check In Date: ") {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.BOLD, 20));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        }};

        JLabel checkOutLabel = new JLabel("Check Out Date: ") {{
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.BOLD, 20));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        }};

        JPanel dateAndBookingPanel = new JPanel();
        dateAndBookingPanel.setBackground(Color.white);
        dateAndBookingPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        dateAndBookingPanel.add(checkInLabel, c);

        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 50);
        dateAndBookingPanel.add(checkInDate, c);

        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        dateAndBookingPanel.add(checkOutLabel, c);

        c.gridx = 3;
        c.insets = new Insets(0, 0, 0, 50);
        dateAndBookingPanel.add(checkOutDate, c);

        c.gridx = 4;
        c.insets = new Insets(0, 0, 0, 50);

        dateAndBookingPanel.add(new JButton("  BOOK  ") {{
            setUI(new BasicButtonUI());
            setBackground(Color.white);
            setForeground(Color.black);
            setFont(new Font("Times New Roman", Font.PLAIN, 30));
            setFocusPainted(false);
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (rooms.isEmpty())
                        return;
                    MongoModifier mongo = new MongoModifier();
                    try {
                        Guest guest = new Guest(mongo.getUser(((ScreenPanel)getParent().getParent().getParent()).getAssociatedUsername()), (LocalDate)checkInDate.getSelectedItem(), (LocalDate)checkOutDate.getSelectedItem(), rooms);
                        for (Room room : rooms) {
                            mongo.removeRoom(room);
                            room.addGuest(guest);
                            mongo.addRoom(room);
                            guest.purchase(StringModifier.addSpace(room.getType()), room.getCost());
                        }
                        System.out.println("Hi");
                        mongo.addGuest(guest);
                        mongo.addBill(guest);

                    }
                    catch (UserNotFoundException | PreexisistingRoomException userNotFoundException) {
                        userNotFoundException.printStackTrace();
                    }
                    System.out.println(getParent().getParent().getParent().getParent().getParent().getParent().getParent());
                    JFrame frame = (JFrame)(getParent().getParent().getParent().getParent().getParent().getParent().getParent());
                    frame.getContentPane().removeAll();
                    frame.add(new ScreenPanel(), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                }
            });
        }}, c);
        add(dateAndBookingPanel, BorderLayout.SOUTH);
    }
}
