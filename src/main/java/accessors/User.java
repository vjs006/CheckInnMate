package accessors;
import feedbackcomponents.FeedbackGiver;
import org.bson.Document;

public class User {
    private String username;
    private String name;
    private String phoneNumber;
    private String emailId;

    public User(String username, String name, String phoneNumber, String emailId) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }
    public User(User user) {
        this.username = user.username;
        this.name = user.name;
        this.phoneNumber = user.phoneNumber;
        this.emailId = user.emailId;
    }
    public User(Document doc) {
        this.username = (String)doc.get("username");
        this.name = (String)doc.get("name");
        this.phoneNumber = (String)doc.get("phonenumber");
        this.emailId = (String)doc.get("email");
    }

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }

}
