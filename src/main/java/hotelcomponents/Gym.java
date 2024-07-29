package hotelcomponents;

import accessors.Guest;
import database.MongoModifier;
import hotelexceptions.GuestNotFoundException;
import screencomponents.pages.UtilizeAmenitiesPagePanel;
import stringoperations.StringModifier;

import java.awt.*;
import java.time.LocalTime;

public class Gym extends Amenity implements RateBased{
    private final int rate = 250;
    private int ratePerHour;
    public Gym(LocalTime openingTime, LocalTime closingTime, int maximumCapacity, int rate) {
        super(openingTime, closingTime, maximumCapacity);
        this.ratePerHour = rate;
    }

    @Override
    public void utilize(Guest guest, int hoursUsed) {
        MongoModifier mongo = new MongoModifier();
        guest.purchase(StringModifier.addSpace(getClass().getSimpleName()) + " x" + hoursUsed + " hours", getRate() * hoursUsed);
        mongo.addBill(guest);
    }

    @Override
    public int getRate() {
        return ratePerHour ;
    }

    @Override
    public void setRate(int rate) {
        this.ratePerHour = rate;
    }
}
