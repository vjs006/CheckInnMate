package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class SingleRoom extends Room {

    public SingleRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public SingleRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }

    @Override
    public String getType() {
        return "SingleRoom";
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
