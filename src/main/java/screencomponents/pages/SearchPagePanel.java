package screencomponents.pages;

import database.MongoModifier;
import hotelcomponents.rooms.Room;
import layouts.WrapLayout;
import screencomponents.NavPanel;
import screencomponents.RoomPanel;
import screencomponents.constraints.DropDownPanel;
import screencomponents.constraints.PriceBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SearchPagePanel extends JPanel {
    private final MongoModifier mongo = new MongoModifier();
    private HashSet<Room> bookedRooms = new HashSet<>();
    private ArrayList<Room> allRooms;
    public SearchPagePanel() {
        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        setLayout(new WrapLayout(FlowLayout.CENTER, 33, 33));

        ArrayList<Room> roomsList = new ArrayList<>();
        for (Room room : mongo.getAllRooms()) {
            if (room.getGuest() == null)
                roomsList.add(room);
            else if (room.getGuest() != null && room.getGuest().getUsername().equals(""))
                roomsList.add(room);
        }
        Collections.sort(roomsList);
        for (Room room : roomsList) {
            add((new RoomPanel(room, new BookingClicked())));
        }
        allRooms = roomsList;
    }
    public void reDisplayRooms() {
        removeAll();
        NavPanel nav = (NavPanel)((JComponent)getParent().getParent().getParent().getParent().getComponent(0)).getComponent(0);
        JSlider lowerCostSlider = ((PriceBar)nav.getComponent(1)).getCostSlider();
        JSlider upperCostSlider = ((PriceBar)nav.getComponent(1)).getAssociatedSlider();
        DropDownPanel capacityPanel = (DropDownPanel)nav.getComponent(3);
        DropDownPanel roomTypePanel = (DropDownPanel)nav.getComponent(4);

        ArrayList<Room> rooms = new ArrayList<>(allRooms);
        ArrayList<Room> removeList = new ArrayList<>();
        for (Room room : rooms) {
            //System.out.println(roomTypePanel.getCurrentSelection().replace(" ", "") +"??" + room.getType() + " " + capacityPanel.getCurrentSelection().equals("ALL"));
            if (!(lowerCostSlider.getValue() <= room.getCost() && room.getCost() <= upperCostSlider.getValue()))
                removeList.add(room);
            if (!(capacityPanel.getCurrentSelection().equals("ALL")) && (Integer.parseInt(capacityPanel.getCurrentSelection()) != room.getCapacity()))
                removeList.add(room);
            if (!(roomTypePanel.getCurrentSelection().replace(" ", "").equals(room.getType())) && !(roomTypePanel.getCurrentSelection().equals("ALL")))
                removeList.add(room);
            /*else if (*/
        }

        rooms.removeAll(removeList);

        ArrayList<Room> roomsList = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getGuest() == null)
                roomsList.add(room);
            else if (room.getGuest() != null && room.getGuest().getUsername().equals(""))
                roomsList.add(room);
        }
        Collections.sort(roomsList);
        for (Room room : roomsList) {
            add((new RoomPanel(room, new BookingClicked())));
        }

        revalidate();
        repaint();
    }

    public HashSet<Room> getBookedRooms() {
        return bookedRooms;
    }

    private class BookingClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (((JButton)e.getSource()).getForeground().equals(Color.black)) {
                bookedRooms.add(((RoomPanel)((JButton)e.getSource()).getParent()).getRoom());
                ((JButton) e.getSource()).setForeground(Color.green);
            }
            else if (((JButton)e.getSource()).getForeground().equals(Color.green)) {
                bookedRooms.remove(((RoomPanel)((JButton)e.getSource()).getParent()).getRoom());
                ((JButton) e.getSource()).setForeground(Color.black);
            }
        }
    }
}
