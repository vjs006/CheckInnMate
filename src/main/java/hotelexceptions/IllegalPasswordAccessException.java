package hotelexceptions;

public class IllegalPasswordAccessException extends Exception {
    @Override
    public String toString() {
        return "Password Access Token is Invalid";
    }
}
