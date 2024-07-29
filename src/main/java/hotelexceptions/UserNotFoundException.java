package hotelexceptions;

public class UserNotFoundException extends Exception {
    @Override
    public String toString() {
        return "User does not exist";
    }
}
