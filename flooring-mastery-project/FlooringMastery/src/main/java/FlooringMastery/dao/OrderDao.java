package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import FlooringMastery.exceptions.FlooringPersistenceException;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {

    /**
     * Adds an order to the storage.
     * @param order The order to add.
     * @return The added order.
     */
    Order addOrder(Order order) throws FlooringPersistenceException;

    /**
     * Retrieves all orders for a specific date.
     * @param date The date for which orders are to be retrieved.
     * @return A list of orders for that date.
     */
    List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException;

    /**
     * Retrieves an order by its order number and date.
     * @param orderNumber The order number of the order to retrieve.
     * @param date The date of the order.
     * @return The order if found, null otherwise.
     */
    Order getOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException;

    /**
     * Updates an existing order.
     * @param order The order with updated information.
     * @return The updated order.
     */
    Order updateOrder(Order order) throws FlooringPersistenceException;

    /**
     * Removes an order.
     * @param orderNumber The order number of the order to remove.
     * @param date The date of the order to remove.
     * @return The removed order, null if no order was found.
     */
    Order removeOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException;

    List<Order> getAllOrders() throws FlooringPersistenceException;

}