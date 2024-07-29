package feedbackcomponents;

import hotelcomponents.HotelComponent;
import org.bson.Document;

import java.time.LocalDate;

public class Feedback implements Comparable<Feedback> {
    private String feedbackString;
    private int rating;
    private String username;
    private LocalDate date;
    private String type;

    public Feedback(String username, String type, int rating, String feedbackString) {
        this.username = username;
        this.feedbackString = feedbackString;
        this.rating = rating;
        this.type = type;
        date = LocalDate.now();
    }

    public Feedback(Document feedbackDoc) {
        this.username = feedbackDoc.getString("username");
        this.feedbackString = feedbackDoc.getString("text");
        this.rating = feedbackDoc.getInteger("rating");
        this.type = feedbackDoc.getString("type");
        date = LocalDate.parse(feedbackDoc.getString("date"));
    }

    public int getRating() {
        return rating;
    }

    public String getFeedbackString() {
        return feedbackString;
    }

    public String getAssociatedUsername() {
        return username;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getType() {
        return type;
    }
    @Override
    public int compareTo(Feedback feedback) {
        return date.compareTo(feedback.date);
    }
}
