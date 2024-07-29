package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class KingRoom extends Room {

    public KingRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public KingRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }
    @Override
    public String getType() {
        return "KingRoom";
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
        return true;
    }

    @Override
    public  String getDescription(){
        return " ";
    }
}
