package payments;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bill {
    private LinkedHashMap<String, Double> purchases = new LinkedHashMap<>();
    private double totalAmount;

    public void addPayment(String reason, double amount) {
        purchases.put(reason, amount);
        totalAmount += amount;
    }
    public double getTotalAmount() {
        return totalAmount;
    }

    public LinkedHashMap<String, Double> getPurchases() {
        return purchases;
    }

    public void printInvoice() {

    }
}
