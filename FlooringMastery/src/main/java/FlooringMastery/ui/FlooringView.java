package FlooringMastery.ui;

import FlooringMastery.dto.Order;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class FlooringView {
    private UserIO io = new UserIOConsoleImpl();
    @Autowired
    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("<< Flooring Program >>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public LocalDate readDate(String prompt) {
        LocalDate date = null;
        while (date == null) {
            try {
                String dateStr = io.readString(prompt);
                date = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                io.print("ERROR: Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        }
        return date;
    }


    public void displayOrders(List<Order> orders) {
        for (Order order : orders) {
            io.print( "Order Date: " + order.getOrderDate() +
                    ", Customer Name: " + order.getCustomerName() +
                    ", State: " + order.getState() +
                    ", Product Type: " + order.getProductType() +
                    ", Area: " + order.getArea() +
                    ", Total Cost: $" + order.getTotal());
        }
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("ERROR: " + errorMsg);
    }

    public Product chooseProduct(List<Product> products) {
        int index = 1;
        for (Product product : products) {
            io.print(index++ + ". " + product.toString());
        }
        int productChoice = io.readInt("Select a product", 1, products.size()) - 1;
        return products.get(productChoice);
    }

    public String chooseState(List<Tax> taxes) {
        int index = 1;
        for (Tax tax : taxes) {
            io.print(index++ + ". " + tax.toString());
        }
        int stateChoice = io.readInt("Select a state", 1, taxes.size()) - 1;
        return taxes.get(stateChoice).getStateAbbrev();
    }

    public BigDecimal readArea() {
        return io.readBigDecimal("Enter area (Minimum 100 sq ft)");
    }

    public void displayOrderDetails_AddOrder(Order order) {
        io.print("Order Details:");
        io.print("Order Date: " + order.getOrderDate());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: "+ order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea() + " sq ft");
        io.print("Cost Per Square Foot: $" + order.getCostPerSquareFoot());
        io.print("Labor Cost Per Square Foot: $" + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print("");
    }


    public void displayOrderDetails(Order order) {
        io.print("Order Details:");
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Order Date: " + order.getOrderDate());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: "+ order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea() + " sq ft");
        io.print("Cost Per Square Foot: $" + order.getCostPerSquareFoot());
        io.print("Labor Cost Per Square Foot: $" + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print("");
    }

    public String readString(String prompt) {
        return io.readString(prompt);
    }
    public int readInt(String prompt) {
        return io.readInt(prompt);
    }
    public BigDecimal readBigDecimal(String prompt) {
        return io.readBigDecimal(prompt);
    }

    public Order editOrder(Order order) {
        String customerName = io.readString("Enter customer name (" + order.getCustomerName() + "):");
        String state = io.readString("Enter state (" + order.getState() + "):");
        String productType = io.readString("Enter product type (" + order.getProductType() + "):");
        BigDecimal area = io.readBigDecimal("Enter area (" + order.getArea() + "):");

        if (!customerName.isEmpty()) {
            order.setCustomerName(customerName);
        }
        if (!state.isEmpty()) {
            order.setState(state);
        }
        if (!productType.isEmpty()) {
            order.setProductType(productType);
        }
        if (area.compareTo(new BigDecimal(100)) > 0) {
            order.setArea(area);
        }

        return order;
    }


    public void displaySuccessMessage(String message) {
        io.print("*** SUCCESS ***");
        io.print(message);
    }

    public boolean getConfirmation(String message) {
        String userInput = io.readString(message);
        return userInput.equalsIgnoreCase("Y");
    }

    public void displayMessage(String message) {
        io.print(message);
    }
}
