package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class CabanaRoom extends Room {

    public CabanaRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public CabanaRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }
    @Override
    public String getType() {
        return "CabanaRoom";
    }

    @Override
    public boolean hasFreeGymAccess() {
        return false;
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
