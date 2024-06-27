package FlooringMastery.service;

import FlooringMastery.dto.Order;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.exceptions.FlooringPersistenceException;
import FlooringMastery.exceptions.FlooringUserInvalidDataException;

import java.time.LocalDate;
import java.util.List;

public interface FlooringServiceLayer {

    /**
     * Adds a new order to the system after validating input data and calculating necessary fields.
     * @param order The order to add, with user-provided details.
     * @return The added order with all fields populated, including calculated values.
     * @throws FlooringPersistenceException if there's an issue persisting the order or if validation fails.
     */
    Order addOrder(Order order) throws FlooringPersistenceException,  FlooringUserInvalidDataException;

    /**
     * Retrieves all orders for a given date.
     * @param date The date for which to retrieve orders.
     * @return A list of orders for the specified date.
     * @throws FlooringPersistenceException if there's an issue accessing the data.
     */
    List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException;

    /**
     * Retrieves a single order by order number and date.
     * @param orderNumber The order number of the order to retrieve.
     * @param date The date of the order.
     * @return The order if found, or null otherwise.
     * @throws FlooringPersistenceException if there's an issue accessing the data.
     */
    Order getOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException;

    /**
     * Updates an existing order with new details, after validating input data and recalculating necessary fields.
     * @param order The order with updated information.
     * @return The updated order.
     * @throws FlooringPersistenceException if there's an issue updating the data or if validation fails.
     */
    Order updateOrder(Order order) throws FlooringPersistenceException, FlooringUserInvalidDataException;

    /**
     * Removes an order from the system.
     * @param orderNumber The order number of the order to remove.
     * @param date The date of the order.
     * @return The removed order, or null if the order was not found.
     * @throws FlooringPersistenceException if there's an issue removing the data.
     */
    Order removeOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException;

    /**
     * Validates the order data, ensuring that all user inputs meet business rules.
     * This could include checking state tax rates, product availability, etc.
     * @param order The order to validate.
     * @throws FlooringUserInvalidDataException if any validation rules are violated.
     */
    void validateOrderData(Order order) throws FlooringUserInvalidDataException, FlooringPersistenceException;
    List<Product> getProducts() throws FlooringPersistenceException;


    List<Tax> getTaxes() throws FlooringPersistenceException;

    void exportAllData() throws FlooringPersistenceException;
    public void calculateOrderCosts(Order order) throws FlooringPersistenceException;
}

