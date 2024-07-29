package hotelcomponents.rooms;
import accessors.Guest;
import org.bson.Document;

public abstract class Room implements Comparable<Room> {
    private int roomId;
    private int capacity;
    private Guest guest;
    private double cost;
    private int area;

    public Room(int roomId, int capacity, double cost, int area, Guest guest) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.cost = cost;
        this.area = area;
        this.guest = guest;
    }

    public Room(Document roomDoc, Guest guest) {
        this.roomId = roomDoc.getInteger("roomId");
        this.capacity = roomDoc.getInteger("capacity");
        this.cost = roomDoc.getInteger("cost");
        this.area = roomDoc.getInteger("area");
        this.guest = guest;
    }

    public Room() {

    }

    public int getRoomId() {
        return roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCost() {
        return cost;
    }

    public int getArea() {
        return area;
    }

    public Guest getGuest() {
        return guest;
    }

    public boolean isOccupied() {
        if (guest == null)
            return false;
        return true;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void addGuest(Guest guest) {
        this.guest = guest;
    }

    public void removeGuest() {
        this.guest = null;
    }

    @Override
    public int compareTo(Room r) {
        return roomId - r.roomId;
    }

    public abstract String getType();
    public abstract boolean hasFreeGymAccess();
    public abstract boolean hasFreeSwimmingPoolAccess();
    public abstract boolean hasMiniBarAccess();
    public abstract boolean hasFreeGameRoomAccess();

    public abstract String getDescription();

}
