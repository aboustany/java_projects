package FlooringMastery.controller;

import FlooringMastery.dao.OrderDao;
import FlooringMastery.dao.OrderDaoDbImpl;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.exceptions.FlooringPersistenceException;
import FlooringMastery.exceptions.FlooringUserInvalidDataException;
import FlooringMastery.service.FlooringServiceLayer;
import FlooringMastery.service.FlooringServiceLayerImpl;
import FlooringMastery.ui.FlooringView;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller("controller")
public class FlooringController {
    private UserIO io = new UserIOConsoleImpl();
    private FlooringView view;
    private FlooringServiceLayer service;
    private final OrderDao orderDao;


    @Autowired
    public FlooringController(UserIO io, FlooringView view, FlooringServiceLayer service, OrderDao orderDao) {
        this.io = io;
        this.view = view;
        this.service = service;
        this.orderDao = orderDao; // Now correctly autowired by Spring
    }

    public void run() throws Exception {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    io.print("DISPLAY ORDERS");
                    displayOrders();
                    break;
                case 2:
                    io.print("ADD AN ORDER");
                    addOrder();
                    break;
                case 3:
                    io.print("EDIT AN ORDER");
                    editOrder();
                    break;
                case 4:
                    io.print("REMOVE AN ORDER");
                    removeOrder();
                    break;
                case 5:
                    io.print("EXPORT ALL DATA");
                    exportAllData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    io.print("UNKNOWN COMMAND");
            }

        }
        io.print("GOOD BYE");
    }

    private void exportAllData() {
        try {
            service.exportAllData();
            view.displaySuccessMessage("All data has been successfully exported to DataExport.txt.");
        } catch (FlooringPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() {
        LocalDate date = view.readDate("Enter the date of the order you want to remove (YYYY-MM-DD):");
        int orderNumber = view.readInt("Enter the order number:");

        try {
            Order orderToRemove = service.getOrder(orderNumber, date);
            if (orderToRemove == null) {
                view.displayErrorMessage("No order found with order number " + orderNumber + " on date " + date);
                return;
            }

            view.displayOrderDetails(orderToRemove); // Show details of the order to be removed
            boolean confirmRemove = view.getConfirmation("Are you sure you want to remove this order? (Y/N)");

            if (confirmRemove) {
                Order removedOrder = service.removeOrder(orderNumber, date);
                if (removedOrder != null) {
                    view.displaySuccessMessage("Order successfully removed.");
                } else {
                    view.displayErrorMessage("Could not remove the order.");
                }
            } else {
                view.displayMessage("Order removal canceled.");
            }
        } catch (FlooringPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }


    private void editOrder() {
        LocalDate date = view.readDate("Enter the date of the order you want to edit (YYYY-MM-DD):");
        int orderNumber = view.readInt("Enter the order number:");

        try {
            Order orderToEdit = service.getOrder(orderNumber, date);
            if (orderToEdit == null) {
                view.displayErrorMessage("No order found with order number " + orderNumber + " on date " + date);
                return;
            }

            // Show current order details
            view.displayOrderDetails(orderToEdit);

            // Get updated order details from the user
            Order updatedOrder = view.editOrder(orderToEdit);

            // Validate updated order details and calculate new costs if necessary
            service.validateOrderData(updatedOrder);
            service.calculateOrderCosts(updatedOrder);

            // Display updated order details for confirmation
            view.displayOrderDetails(updatedOrder);

            // Ask for confirmation to proceed with the update
            boolean confirm = view.getConfirmation("Do you want to save these changes? (Y/N): ");
            if (confirm) {
                // Update the order in the system
                Order editedOrder = service.updateOrder(updatedOrder);

                // Show updated order details
                view.displayOrderDetails(editedOrder);

                view.displaySuccessMessage("Order successfully updated.");
            } else {
                view.displayMessage("Update cancelled.");
            }
        } catch (FlooringPersistenceException | FlooringUserInvalidDataException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }



    private void displayOrders() {
        LocalDate date = view.readDate("Enter the date for which you want to display orders (YYYY-MM-DD):");
        try {
            List<Order> orders = service.getOrdersByDate(date);
            view.displayOrders(orders);
        } catch (FlooringPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }


    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }


    private void addOrder() {
        try {
            List<Product> products = service.getProducts();
            List<Tax> taxes = service.getTaxes();

            Product selectedProduct = view.chooseProduct(products);
            String state = view.chooseState(taxes);
            BigDecimal area = view.readArea();
            String customerName = view.readString("Enter customer name");
            String orderDate = String.valueOf(view.readDate("Enter order date (YYYY-MM-DD)"));
            Order newOrder = new Order(orderDate, customerName, state, selectedProduct.getProductType(), area);
            service.validateOrderData(newOrder);
            service.calculateOrderCosts(newOrder);
            view.displayOrderDetails_AddOrder(newOrder);
            boolean confirmAdd = view.getConfirmation("Are you sure you want to add this order? (Y/N)");
            if (!confirmAdd) {
                view.displayMessage("Order addition canceled.");
                return;
            }
            Order addedOrder = service.addOrder(newOrder); // Persists the order
            view.displayMessage("Order added successfully. ASSIGNED ORDER NUMBER: " + addedOrder.getOrderNumber() );
        } catch (FlooringPersistenceException | FlooringUserInvalidDataException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

}
