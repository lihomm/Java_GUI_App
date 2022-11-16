package BackEnd;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String ID;
    private List<String> foods;
    private String paid;
    private String feedback;

    public Order(String ID) {
        this.ID = ID;
        foods = new ArrayList<>();
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPaid() {
        return paid;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }
}
