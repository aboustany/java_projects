package VendingMachine.ui;

import VendingMachine.dto.Change;
import VendingMachine.dto.MachineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class VendingMachineView {
    
    private UserIO io;
    @Autowired
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    public void displayErrorMessage(String message) {
        io.print("=== ERROR ===");
        io.print(message);
    }

    public void displayAllItemsBanner() {
        io.print("=== Item List ===");
    }

    public void displayItems(List<MachineItem> items) {
        io.print("Available Items:");
        int index = 1;
        for (MachineItem item : items) {
            String itemInfo = String.format("%d. %s - Price: %.2f, Quantity: %d",
                    index++, item.getName(), item.getPrice(), item.getQuantity());
            io.print(itemInfo);
        }
    }

    public void displayUnknownCommandMessage() {
        io.print("Unknown command. Please try again.");
    }

    public void displayCollectPaymentBanner() {
        io.print("=== Payment Collection ===");
        io.print("Please insert payment.");
    }

    public void displayWelcomeBanner() {
        io.print("=== Welcome to the Vending Machine ===");
    }



    public BigDecimal collectPayment() {
       return BigDecimal.valueOf(io.readDouble("Please insert payment (whole numbers only):"));
    }


    public void displaySelectItemBanner() {
        io.print("=== Select Item ===");
    }

    public int getItemSelection(int itemCounts) {
        return io.readInt("Please select an item by entering the corresponding number:", 1, itemCounts);
    }

    public void displaySuccessBanner(String name) {
        io.print("Thank you for your purchase of " + name);
    }

    public void displayChange(BigDecimal change) {
        io.print("Your change is: $" + change);
    }


    public void displayChangeCoins(Map<Change.Coin, Integer> changeCoins) {
        if (changeCoins.isEmpty()) {
            io.print("No change to return.");
        } else {
            io.print("Coins returned:");
            changeCoins.forEach((coin, quantity) -> {
                if (quantity > 0) { // Only display if there's at least one coin of this type to return
                    io.print(quantity + " x " + coin.name() + "(s) [" + coin.getValue() + "]");
                }
            });
        }
    }

}
