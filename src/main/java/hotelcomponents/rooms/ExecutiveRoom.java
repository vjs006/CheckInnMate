package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public class ExecutiveRoom extends Room {

    public ExecutiveRoom(int roomId, int capacity, double cost, int area, Guest guest) {
        super(roomId, capacity, cost, area, guest);
    }
    public ExecutiveRoom(Document roomDoc, Guest guest) {
        super(roomDoc, guest);
    }
    @Override
    public String getType() {
        return "ExecutiveRoom";
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
