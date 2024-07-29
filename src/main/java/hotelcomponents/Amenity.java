package hotelcomponents;

import accessors.Guest;
import accessors.User;

import java.time.LocalTime;

public abstract class Amenity extends HotelComponent {
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int maximumCapacity;
    private int currentCapacity;

    public Amenity(LocalTime openingTime, LocalTime closingTime, int maximumCapacity) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.maximumCapacity = maximumCapacity;
    }


    public boolean isOpen(LocalTime time) {

        int t1 = time.compareTo(openingTime);

        int t2 = time.compareTo(closingTime);
        return (t1 > 0) && (t2 < 0);
        //Not implemented yet
    }
    public boolean addUser() {
        if(!isFull(currentCapacity)){
            currentCapacity++;
            return true;
        }
         //Not fully implemented
        return false ;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    // if added true, else false
    public boolean isFull(int currentCapacity) {
        return currentCapacity == maximumCapacity;//Not implemented yet
    }
    public abstract void utilize(Guest guest,int hoursUsed);
    // modify the bill ( )
}
