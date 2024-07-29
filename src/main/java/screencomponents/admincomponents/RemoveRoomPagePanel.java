package screencomponents.admincomponents;

import database.MongoModifier;
import hotelcomponents.rooms.Room;
import layouts.WrapLayout;
import screencomponents.RoomPanel;
import screencomponents.pages.SearchPagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class RemoveRoomPagePanel extends JPanel{

    private MongoModifier mongo = new MongoModifier();
    private ArrayList<Room> allRooms;
    public RemoveRoomPagePanel(){
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
            add((new RemoveRoomPanel(room, new RemoveClicked(room))));
        }
        allRooms = roomsList;
    }
    public void reDisplayRooms() {
        removeAll();


        ArrayList<Room> roomsList = new ArrayList<>();
        for (Room room : allRooms) {
            if (room.getGuest() == null)
                roomsList.add(room);
            else if (room.getGuest() != null && room.getGuest().getUsername().equals(""))
                roomsList.add(room);
        }
        Collections.sort(roomsList);
        for (Room room : roomsList) {
            add((new RemoveRoomPanel(room, new RemoveClicked(room))));
        }

        revalidate();
        repaint();
    }
    private class RemoveClicked implements ActionListener {
        Room room;

        RemoveClicked(Room room) {
            this.room = room;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton srcButton = ((JButton)e.getSource());
            if (srcButton.getText().equals("REMOVE")){
                srcButton.setForeground(Color.RED);
                srcButton.setText("CONFIRM");
            } else if (srcButton.getText().equals("CONFIRM")) {
                mongo.removeRoom(room);
                reDisplayRooms();
            }

        }
    }
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(1200, 800);
        window.setTitle("CheckInnMate");
        //window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.white);

        window.add(new RemoveRoomPagePanel());
        window.setVisible(true);
    }


}
