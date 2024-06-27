package VendingMachine.dto;

import java.math.BigDecimal;

public class MachineItem {
    private String name;
    private BigDecimal price;
    private int quantity;

    public MachineItem(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}
