package screencomponents.pages;

import accessors.Guest;
import database.MongoModifier;
import hotelcomponents.Amenity;
import hotelcomponents.RateBased;
import hotelexceptions.GuestNotFoundException;
import screencomponents.AppDefaultButton;
import screencomponents.ScreenPanel;
import stringoperations.StringModifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class UseAmenityPagePanel<T extends Amenity & RateBased> extends JPanel {
    private T amenity;
    private JComboBox<Integer> hoursBox;
    public UseAmenityPagePanel(T amenity) {

        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setLayout(new GridBagLayout());

        this.amenity = amenity;
    }

    @Override
    public void addNotify() {
        super.addNotify();

        ScreenPanel screenPanel = (ScreenPanel) getParent();
        boolean isFree = false;
        boolean invalidTime = false;
        switch(StringModifier.addSpace(amenity.getClass().getSimpleName())) {
            case "Gym" :
                if (screenPanel.getAssociatedGuest().getMostValubleRoom().hasFreeGymAccess())
                    isFree = true;
                break;
            case "Game Room" :
                if (screenPanel.getAssociatedGuest().getMostValubleRoom().hasFreeGameRoomAccess())
                    isFree = true;
                break;
            case "Swimming Pool" :
                if (screenPanel.getAssociatedGuest().getMostValubleRoom().hasFreeSwimmingPoolAccess())
                    isFree = true;
                break;
        }

        if (LocalTime.now().isBefore(amenity.getOpeningTime()) || LocalTime.now().isAfter(amenity.getClosingTime())) {
            add(new JLabel("This Amenity is closed at this hour.") {{
                setFont(new Font("Times New Roman", Font.PLAIN, 40));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.red);
            }});
            add(new JLabel("Access Times are from : " + amenity.getOpeningTime() + " to " + amenity.getClosingTime()) {{
                setFont(new Font("Times New Roman", Font.PLAIN, 30));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.red);
            }}, new GridBagConstraints() {{
                gridy = 1;
            }});
        }
        else if(isFree) {
            add(new JLabel("YOU MAY FREELY ACCESS THIS AMENITY!") {{
                setFont(new Font("Times New Roman", Font.PLAIN, 40));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.green);
            }});
        }
        else {
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Color.white);
            centerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 4));
            centerPanel.setLayout(new GridLayout(3, 1));

            centerPanel.add(new JLabel(StringModifier.addSpace(amenity.getClass().getSimpleName())) {{
                setFont(new Font("Times New Roman", Font.BOLD, 40));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setForeground(Color.black);
            }});

            centerPanel.add(new JPanel() {{
                setBackground(Color.white);
                setLayout(new GridBagLayout());
                add(new JLabel("Hours of Use: ") {{
                    setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    setHorizontalAlignment(JLabel.CENTER);
                    setVerticalAlignment(JLabel.CENTER);
                    setForeground(Color.black);
                }}, new GridBagConstraints());


                add(hoursBox = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5, 6}), new GridBagConstraints() {{
                    gridx = 1;
                }});
            }});

            centerPanel.add(new JPanel() {{
                setBackground(Color.white);
                setLayout(new GridBagLayout());
                add(new AppDefaultButton("USE") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                MongoModifier mongo = new MongoModifier();
                                Guest guest = mongo.getGuest(screenPanel.getAssociatedUsername());
                                amenity.utilize(guest, (int)hoursBox.getSelectedItem());
                                screenPanel.setAssociatedGuest(guest);
                                
                                screenPanel.remove(1);
                                screenPanel.add(new UtilizeAmenitiesPagePanel(), BorderLayout.CENTER);
                                screenPanel.revalidate();
                                screenPanel.repaint();
                            }
                            catch (GuestNotFoundException guestNotFoundException) {
                                guestNotFoundException.printStackTrace();
                            }
                        }
                    });
                }}, new GridBagConstraints());
            }});
            add(centerPanel, new GridBagConstraints());
        }
    }
}
