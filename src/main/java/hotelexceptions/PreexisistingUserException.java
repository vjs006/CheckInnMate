package hotelexceptions;

public class PreexisistingUserException extends Exception{
    @Override
    public String toString() {
        return "This username already Exists";
    }
}
