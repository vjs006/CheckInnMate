package payments;

import java.util.HashMap;

public class Product {
    private String pname;
    private final int quantity = 1;
    private Double priceperpiece;
    public Product(String pname, Double priceperpiece) {
        this.pname = pname;
        this.priceperpiece = priceperpiece;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public Double getPriceperpiece() {
        return priceperpiece;
    }
    public void setPriceperpiece(Double priceperpiece) {
        this.priceperpiece = priceperpiece;
    }
}
