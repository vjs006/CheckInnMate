package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class DoubleRoom extends Room {

    public DoubleRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public DoubleRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }
    @Override
    public String getType() {
        return "DoubleRoom";
    }

    @Override
    public boolean hasFreeGymAccess() {
        return false;
    }

    @Override
    public boolean hasFreeSwimmingPoolAccess() {
        return false;
    }

    @Override
    public boolean hasMiniBarAccess() {
        return false;
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
