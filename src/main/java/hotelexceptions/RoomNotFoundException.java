package hotelexceptions;

public class RoomNotFoundException extends Exception{
    @Override
    public String toString() {
        return "Room does not exist";
    }
}
