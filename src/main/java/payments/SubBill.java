package payments;

import java.util.HashMap;

public class SubBill {
    private HashMap<String, Double> purchases = new HashMap<>();
    private double totalAmount;

    public SubBill() {
        this.purchases = new HashMap<>();
        this.totalAmount = 0.0;
    }

    public void addPayment(String reason, double amount) {
        purchases.put(reason, amount);
        totalAmount += amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public HashMap<String, Double> getPurchases() {
        return purchases;
    }

    public String printInvoice() {
        return ""; // Yet to implement
    }
}
