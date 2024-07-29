package accessors;

import com.mongodb.client.MongoDatabase;
import database.MongoModifier;
import feedbackcomponents.Feedback;
import feedbackcomponents.FeedbackGiver;
import hotelcomponents.rooms.Room;
import hotelexceptions.UserNotFoundException;
import org.bson.Document;
import payments.Bill;

import java.time.*;
import java.util.Arrays;
import java.util.HashSet;

public class Guest extends User implements FeedbackGiver, Comparable<Guest> {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private HashSet<Room> rooms;
    private Bill bill;

    public Guest(User user, LocalDate checkInDate, LocalDate checkOutDate, HashSet<Room> rooms) throws UserNotFoundException {
        super(user);
        checkInTime = checkInDate.atTime(14, 0, 0);
        checkOutTime = checkOutDate.atTime(12, 0, 0);; //Will come up with formula later
        bill = (new MongoModifier()).getBill(getUsername());
        this.rooms = rooms;
    }
    public Guest(Document guestDoc, HashSet<Room> rooms) throws UserNotFoundException {
        super((new MongoModifier()).getUser(guestDoc.getString("originalUsername")));
        checkInTime = LocalDateTime.parse(guestDoc.getString("checkInTime"));
        checkOutTime = LocalDateTime.parse(guestDoc.getString("checkOutTime"));
        bill = (new MongoModifier()).getBill(getUsername());
        this.rooms = rooms;
    }
    public Guest(Document guestDoc) throws UserNotFoundException {
        super((new MongoModifier()).getUser(guestDoc.getString("originalUsername")));
        checkInTime = LocalDateTime.parse(guestDoc.getString("checkInTime"));
        checkOutTime = LocalDateTime.parse(guestDoc.getString("checkOutTime"));
        bill = (new MongoModifier()).getBill(getUsername());
        this.rooms = null;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public Bill getBill() {
        return bill;
    }
    public void purchase(String item, double amount) {
        bill.addPayment(item, amount);
    }
    public void checkOut() {

    }

    public HashSet<Room> getRooms() {
        return rooms;
    }

    public Room getMostValubleRoom() {
        return new Room() {
            @Override
            public String getType() {
                return "MostValuble";
            }

            @Override
            public boolean hasFreeGymAccess() {
                boolean b = false;
                for (Room room : rooms) {
                    b = b | room.hasFreeGymAccess();
                }
                return b;
            }

            @Override
            public boolean hasFreeSwimmingPoolAccess() {
                boolean b = false;
                for (Room room : rooms) {
                    b = b | room.hasFreeSwimmingPoolAccess();
                }
                return b;
            }

            @Override
            public boolean hasMiniBarAccess() {
                boolean b = false;
                for (Room room : rooms) {
                    b = b | room.hasMiniBarAccess();
                }
                return b;
            }

            @Override
            public boolean hasFreeGameRoomAccess() {
                boolean b = false;
                for (Room room : rooms) {
                    b = b | room.hasFreeGameRoomAccess();
                }
                return b;
            }

            @Override
            public String getDescription() {
                return null;
            }
        };
    }
    @Override
    public int compareTo(Guest guest) {
        return (int) Duration.between(checkOutTime, guest.checkOutTime).getSeconds();
    }

    @Override
    public void giveFeedback(Feedback feedback) {
        MongoModifier mongo = new MongoModifier();
        mongo.addFeedback(feedback);
    }
}
