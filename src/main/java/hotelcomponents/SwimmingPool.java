package hotelcomponents;

import accessors.Guest;
import database.MongoModifier;
import stringoperations.StringModifier;

import java.time.LocalTime;

public class SwimmingPool extends Amenity implements RateBased {
    private int ratePerHour;

    public SwimmingPool(LocalTime openingTime, LocalTime closingTime, int maximumCapacity, int rate) {
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
        return ratePerHour;
    }

    @Override
    public void setRate(int rate) {
        this.ratePerHour = rate;
    }
}
