package database;

import accessors.Guest;
import accessors.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import feedbackcomponents.Feedback;
import hotelcomponents.HotelComponent;
import hotelcomponents.rooms.*;
import hotelexceptions.*;
import org.bson.Document;
import payments.Bill;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

public class MongoModifier {
    private static final String connectionString = "mongodb+srv://HotelUser:4hzn3PHUVtyFsCHP@checkinnmatecluster.9vr7fw9.mongodb.net/?retryWrites=true&w=majority";
    private static final String PASSWORD_ACCESS_TOKEN = "awZYbHxLl4WykOhSiujjJm7U9VuEYx2X";
    private MongoClient mongoClient;
    private MongoDatabase mongoUserDatabase, mongoHotelDatabase, mongoAdminDatabase;
    private MongoCollection<Document> usersCollection, logsCollection, feedbackCollection, roomsCollection, guestsCollection, billsCollection;

    public MongoModifier() {
        /*MongoClientSettings settings = MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(1) // Adjust this value based on your needs
                )
                .applyConnectionString(new ConnectionString(connectionString))
                // other settings...
                .build();
        mongoClient = MongoClients.create(settings);*/
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        mongoUserDatabase = mongoClient.getDatabase("Userbase");;
        mongoHotelDatabase = mongoClient.getDatabase("Hotelbase");
        mongoAdminDatabase = mongoClient.getDatabase("Adminbase");
    }

    public void addUser(User user, String password) throws PreexisistingUserException {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Users");
        try {
            getUser(user.getUsername());
            throw new PreexisistingUserException();
        }
        catch (UserNotFoundException e) {
            Document document = new Document("username", user.getUsername())
                    .append("name", user.getName())
                    .append("phonenumber", user.getPhoneNumber())
                    .append("email", user.getEmailId())
                    .append("password", password);
            collection.insertOne(document);
        }
    }
    public boolean removeUser(User user) {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Users");
        try {
            getUser(user.getUsername());
            collection.deleteOne(Filters.eq("username", user.getUsername()));
            return true;
        }
        catch (UserNotFoundException e) {
            return false;
        }
    }
    public User getUser(String username) throws UserNotFoundException {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Users");
        Document query = new Document("username", username);
        Document result = collection.find(query).first();
        if (result == null)
            throw new UserNotFoundException();
        return new User(result);
    }

    public HashSet<User> getAllUsers() {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Users");
        MongoCursor<Document> result = collection.find().iterator();
        HashSet<User> users = new HashSet<>();
        while(result.hasNext()) {
            users.add(new User(result.next()));
        }
        return users;
    }
    public String getPassword(String username, String passwordAccessToken) throws IllegalPasswordAccessException, UserNotFoundException {
        if (!(passwordAccessToken.equals(PASSWORD_ACCESS_TOKEN)))
            throw new IllegalPasswordAccessException();
        else {
            MongoDatabase database = mongoUserDatabase;
            MongoCollection<Document> collection = database.getCollection("Users");
            Document query = new Document("username", username);
            Document result = collection.find(query).first();
            if (result == null)
                throw new UserNotFoundException();
            return result.getString("password");
        }
    }
    public HashSet<Room> getAllRooms() {
        return getRooms("");
    }

    public HashSet<Room> getRooms(String guestUsername) {
        try {
            MongoDatabase database = mongoHotelDatabase;
            MongoCollection<Document> collection = database.getCollection("Rooms");
            MongoCursor<Document> results;
            if (guestUsername.equals(""))
                results = collection.find().iterator();
            else
                results = collection.find(new Document("guest", guestUsername)).iterator();
            HashSet<Room> rooms = new HashSet<>();
            while(results.hasNext()) {
                Document roomDoc = results.next();
                //System.out.println("HELLO THEREEEEEEE: " + roomDoc.get("roomId"));
                try {
                    rooms.add((Room) Class.forName("hotelcomponents.rooms." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, getRoomlessGuest(roomDoc.getString("guest"))));
                }
                catch (GuestNotFoundException e) {
                    System.out.println(rooms);
                    rooms.add((Room) Class.forName("hotelcomponents.rooms." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, null));
                }
            }
            return rooms;
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("The given user was not found");
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Bill getBill(String guestUsername) {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Bills");
        Document result = collection.find(new Document("guest", guestUsername)).first();
        try {
            if (result == null)
                throw new BillNotFoundException();
            Bill bill = new Bill();
            for (String key : result.keySet()) {
                if (!(key.equals("_id") || key.equals("guest"))) {
                    bill.addPayment(key, result.getDouble(key));
                }
            }
            return bill;
        }
        catch (BillNotFoundException e) {
            createBill(guestUsername);
            return new Bill();
        }

    }

    public Guest getGuest(String guestUsername) throws GuestNotFoundException {
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Guests");
        Document result = collection.find(new Document("originalUsername", guestUsername)).first();
        if (result == null)
            throw new GuestNotFoundException();
        Guest guest = null;
        try {
            HashSet<Room> rooms = getRooms(guestUsername);
            guest = new Guest(result, rooms);
            for (Room room : rooms)
                room.addGuest(guest);
        }
        catch (UserNotFoundException e) {
            System.out.println("The given user was not found");
        }
        return guest;
    }

    public Guest getRoomlessGuest(String guestUsername) throws GuestNotFoundException{
        MongoDatabase database = mongoUserDatabase;
        MongoCollection<Document> collection = database.getCollection("Guests");
        Document result = collection.find(new Document("originalUsername", guestUsername)).first();
        if (result == null)
            throw new GuestNotFoundException();
        Guest guest = null;
        try {
            guest = new Guest(result, null);
        }
        catch (UserNotFoundException e) {
            System.out.println("The given user was not found");
        }
        return guest;
    }

    //

    public void addGuest(Guest guest){
        MongoDatabase database = mongoClient.getDatabase("Userbase");
        MongoCollection<Document> collection = database.getCollection("Guests");

        Document document = new Document("checkInTime", guest.getCheckInTime().toString())
                .append("checkOutTime", guest.getCheckOutTime().toString())
                .append("originalUsername", guest.getUsername());
        collection.insertOne(document);

    }
    public boolean removeGuest(Guest guest){
        MongoDatabase database = mongoClient.getDatabase("Userbase");
        MongoCollection<Document> collection = database.getCollection("Guests");

        Document query = new Document("originalUsername", guest.getUsername());
        Document result = collection.find(query).first();
        if(result == null){
            return false;
        }
        collection.deleteOne(query);
        return true;
    }

    public void createBill(String username) {
        MongoDatabase database = mongoClient.getDatabase("Userbase");
        MongoCollection<Document> collection = database.getCollection("Bills");
        collection.insertOne(new Document().append("guest", username));
    }
    public void addBill(Guest guest){
        MongoDatabase database = mongoClient.getDatabase("Userbase");
        MongoCollection<Document> collection = database.getCollection("Bills");

        collection.findOneAndDelete(new Document("guest", guest.getUsername()));

        Bill bill = guest.getBill();
        Document document = new Document("guest", guest.getUsername());
        for(String key: bill.getPurchases().keySet()){
            document.append(key, bill.getPurchases().get(key));
        }
        collection.insertOne(document);
    }

    public boolean removeBill(Guest guest){
        MongoDatabase database = mongoClient.getDatabase("Userbase");
        MongoCollection<Document> collection = database.getCollection("Bills");

        Document query = new Document("guest", guest.getUsername());
        Document result = collection.find(query).first();
        if(result == null){
            return false;
        }
        collection.deleteOne(query);
        return true;
    }
    public void updateBill(Guest guest){
        removeBill(guest);
        addBill(guest);
    }
    public Room getRoom(int roomID) throws RoomNotFoundException {
        MongoCollection<Document> collection = mongoHotelDatabase.getCollection("Rooms");

        try{
            Document query = new Document("roomID", roomID);
            Document result = collection.find(query).first();

            if (result == null){
                throw new RoomNotFoundException();
            }
            try {
                return (Room) Class.forName("hotelcomponents.rooms." + result.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(result, getRoomlessGuest(result.getString("guest")));
            }
            catch (GuestNotFoundException ex) {
                return (Room) Class.forName("hotelcomponents.rooms." + result.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(result, null);
            }
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void addRoom(Room room) throws PreexisistingRoomException {
        MongoDatabase database = mongoClient.getDatabase("Hotelbase");
        MongoCollection<Document> collection = database.getCollection("Rooms");

        try {
            getRoom(room.getRoomId());
            throw new PreexisistingRoomException();
        }
        catch (RoomNotFoundException e) {
            try {
                Document document = new Document("roomId", room.getRoomId())
                        .append("capacity", room.getCapacity())
                        .append("cost", (int)room.getCost())
                        .append("area", room.getArea())
                        .append("roomType", room.getType())
                        .append("guest", room.getGuest().getUsername());
                collection.insertOne(document);
            }
            catch (NullPointerException ex) {
                Document document = new Document("roomId", room.getRoomId())
                        .append("capacity", room.getCapacity())
                        .append("cost", (int)room.getCost())
                        .append("area", room.getArea())
                        .append("roomType", room.getType())
                        .append("guest", "");
                collection.insertOne(document);
            }
        }
    }

    public boolean removeRoom(Room room){
        MongoDatabase database = mongoClient.getDatabase("Hotelbase");
        MongoCollection<Document> collection = database.getCollection("Rooms");

        Document query = new Document("roomId", room.getRoomId());
        Document result = collection.find(query).first();
        if(result == null){
            System.out.println(query);
            return false;
        }
        collection.deleteOne(query);
        return true;
    }

    void updateRoom(Room room) {
        removeRoom(room);
        try{
            addRoom(room);
        }
        catch(PreexisistingRoomException e){}
    }

    public HashSet<Room> getRoomsByType(String roomType){
        try {
            MongoDatabase database = mongoClient.getDatabase("Hotelbase");
            MongoCollection<Document> collection = database.getCollection("Rooms");
            MongoCursor<Document> results;
            if (roomType.equals(""))
                results = collection.find().iterator();
            else
                results = collection.find(new Document("roomType", roomType)).iterator();
            HashSet<Room> rooms = new HashSet<>();
            while(results.hasNext()) {
                Document roomDoc = results.next();
                rooms.add((Room)Class.forName("hotelcomponents." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, null));
            }
            return rooms;
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("The given user was not found");
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashSet<Room> getRooms(int capacity){
        try {
            MongoDatabase database = mongoClient.getDatabase("Hotelbase");
            MongoCollection<Document> collection = database.getCollection("Rooms");
            MongoCursor<Document> results;
            results = collection.find(new Document("capacity", capacity)).iterator();
            HashSet<Room> rooms = new HashSet<>();
            while(results.hasNext()) {
                Document roomDoc = results.next();
                rooms.add((Room)Class.forName("hotelcomponents." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, null));
            }
            return rooms;
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("The given user was not found");
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public HashSet<Room> getRooms(int min, int max){
        try {
            MongoDatabase database = mongoClient.getDatabase("Hotelbase");
            MongoCollection<Document> collection = database.getCollection("Rooms");
            MongoCursor<Document> results;
            results = collection.find().iterator();
            HashSet<Room> rooms = new HashSet<>();
            while(results.hasNext()) {
                Document roomDoc = results.next();
                int cap = (int)roomDoc.getInteger("capacity");
                if ((min <= cap) && (cap <= max))
                    rooms.add((Room)Class.forName("hotelcomponents." + roomDoc.getString("roomType")).getConstructor(Document.class, Guest.class).newInstance(roomDoc, null));
            }
            return rooms;
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("The given user was not found");
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    //

    public void addFeedback(Feedback feedback) {
        MongoDatabase database = mongoAdminDatabase;
        MongoCollection<Document> collection = database.getCollection("Feedback");

        collection.insertOne(new Document()
                .append("username", feedback.getAssociatedUsername())
                .append("type", feedback.getType())
                .append("rating", feedback.getRating())
                .append("text", feedback.getFeedbackString())
                .append("date", feedback.getDate().toString()));
    }

    public ArrayList<Feedback> getFeedback(String targetType) {
        MongoDatabase database = mongoAdminDatabase;
        MongoCollection<Document> collection = database.getCollection("Feedback");
        MongoCursor<Document> result = collection.find(new Document("type", targetType)).iterator();
        ArrayList<Feedback> list = new ArrayList<>();

        while (result.hasNext()) {
            Document doc = result.next();
            list.add(new Feedback(doc));
        }

        return list;
    }

    public ArrayList<Feedback> getFeedback() {
        MongoDatabase database = mongoAdminDatabase;
        MongoCollection<Document> collection = database.getCollection("Feedback");
        MongoCursor<Document> result = collection.find().iterator();
        ArrayList<Feedback> list = new ArrayList<>();

        while (result.hasNext()) {
            Document doc = result.next();
            list.add(new Feedback(doc));
        }

        return list;
    }

    public static void main(String[] args) {
        MongoModifier mod = new MongoModifier();
        /*try {
            mod.addUser(new User("vishalsairam01", "Vishal Sairam", "vishalsairam01@gmail.com", "+91 9384851772"));
        }
        catch (PreexisistingUserException e) {
            System.out.println(e);
        }*/

        mod.removeUser(new User("vishalsairam", "Vishal Sairam", "vishalsairam01@gmail.com", "+91 9384851772"));
    }
}
