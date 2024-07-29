package hotelcomponents;

import accessors.Guest;
import database.MongoModifier;

import java.time.LocalTime;
import java.util.HashMap;

public class Restaurant extends Amenity {
    private HashMap<String, Integer> menu;

    public Restaurant(LocalTime openingTime, LocalTime closingTime, int maximumCapacity) {
        super(openingTime, closingTime, maximumCapacity);
        setMenu();
    }

    private void setMenu() {
        menu = new HashMap<>();
        menu.put("Paneer Butter Masala",250);
        menu.put("Dal Tadka",200);
        menu.put("Malai Kofta",250);
        menu.put("Roti",50);
        menu.put("Naan",70);
        menu.put("Idlis",32);
        menu.put("Dosa",65);
        menu.put("Poori masala",80);
        menu.put("Parotta",55);
        menu.put("Fried Rice",120);
        menu.put("Pav Bhaji",90);

    }

    public void purchaseFoodItems(Guest guest, HashMap<String,Integer> orders){
        for (String key : orders.keySet()){
            guest.purchase (key, orders.get(key));
        }
    }
    public HashMap<String,Integer> getMenu(){
        return menu;
    }
    @Override
    public void utilize(Guest guest, int hoursUsed) {
        if (hoursUsed!=0){
            guest.purchase ("RestaurantServices x " + hoursUsed ,hoursUsed);
        }
        MongoModifier mongo = new MongoModifier();
       // mongo.updateBill(guest);
    }
}
