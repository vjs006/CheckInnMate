package hotelexceptions;

public class PreexisistingRoomException extends Exception{
    @Override
    public String toString() {
        return "Room already exists";
    }
}
