package screencomponents.admincomponents;

import database.MongoModifier;
import feedbackcomponents.Feedback;
import layouts.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckFeedbackPagePanel extends JPanel {

    public CheckFeedbackPagePanel() {
        setBackground(Color.white);
        setLayout(new WrapLayout(FlowLayout.CENTER, 20, 20));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));

        MongoModifier mongo = new MongoModifier();
        ArrayList<Feedback> feedbacks = mongo.getFeedback();

        for (Feedback feedback : feedbacks) {
            add(new FeedbackPanel(feedback));
        }
    }
}
