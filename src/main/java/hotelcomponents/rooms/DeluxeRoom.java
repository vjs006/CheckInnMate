package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class DeluxeRoom extends Room {

    public DeluxeRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public DeluxeRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }
    @Override
    public String getType() {
        return "DeluxeRoom";
    }

    @Override
    public boolean hasFreeGymAccess() {
        return true;
    }

    @Override
    public boolean hasFreeSwimmingPoolAccess() {
        return true;
    }

    @Override
    public boolean hasMiniBarAccess() {
        return true;
    }

    @Override
    public boolean hasFreeGameRoomAccess() {
        return false;
    }

    @Override
    public  String getDescription(){
        return " ";
    }
}
