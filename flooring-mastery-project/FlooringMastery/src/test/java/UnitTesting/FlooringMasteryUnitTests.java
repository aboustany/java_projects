package UnitTesting;

import FlooringMastery.FlooringMasteryTestConfig;
import FlooringMastery.dao.OrderDao;
import FlooringMastery.dao.ProductDao;
import FlooringMastery.dao.TaxDao;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.dto.Order;
import FlooringMastery.exceptions.FlooringPersistenceException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FlooringMasteryTestConfig.class)
public class FlooringMasteryUnitTests {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private TaxDao taxDao;
    @Autowired
    private OrderDao orderDao;

    public void setUp() throws FlooringPersistenceException {
        List<Product> products = productDao.getProducts();
        List<Tax> taxes = taxDao.getTaxes();
    }

    @Test
    @Transactional
    void testGetAllProducts() throws FlooringPersistenceException {
        List<Product> products = productDao.getProducts();
        assertNotNull(products, "The list of products should not be null");
        assertFalse(products.isEmpty(), "The list of products should not be empty");
    }

    @Test
    void testGetProductByType() throws FlooringPersistenceException {
        Product product = productDao.getProductByType("Tile");
        assertNotNull(product, "The retrieved product should not be null");
        assertEquals("Tile", product.getProductType(), "The product type should match");
    }

    @Test
    void testGetAllTaxes() throws FlooringPersistenceException {
        List<Tax> taxes = taxDao.getTaxes();
        assertNotNull(taxes, "The list of taxes should not be null");
        assertFalse(taxes.isEmpty(), "The list of taxes should not be empty");
    }

    @Test
    void testGetTaxByState() throws FlooringPersistenceException {
        Tax tax = taxDao.getTaxByState("TX");
        assertNotNull(tax, "The retrieved tax should not be null");
        assertEquals("TX", tax.getStateAbbrev(), "The state abbreviation should match");
    }

    @Test
    void testGetNonexistentProduct() {
        assertThrows(FlooringPersistenceException.class, () -> {
            productDao.getProductByType("Nonexistent");
        }, "Expected an FlooringPersistenceException to be thrown for a nonexistent product type");
    }

    @Test
    void testGetNonexistentTax() {
        assertThrows(FlooringPersistenceException.class, () -> {
            taxDao.getTaxByState("ZZ");
        }, "Expected an FlooringPersistenceException to be thrown for a nonexistent state abbreviation");
    }

    @Test
    void testAddAndGetOrder() throws FlooringPersistenceException {

        Order testOrder = new Order( LocalDate.now().toString(), "Test Customer", "TX",
                new BigDecimal(1), "Tile", new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
        Order addedOrder = orderDao.addOrder(testOrder);
        assertNotNull(addedOrder, "The added order should not be null");

        Order retrievedOrder = orderDao.getOrder(addedOrder.getOrderNumber(), LocalDate.parse(addedOrder.getOrderDate()));
        assertEquals(testOrder.getCustomerName(), retrievedOrder.getCustomerName(), "The order retrieved should have the same customer name as the order added");

        // Clean up
        orderDao.removeOrder(addedOrder.getOrderNumber(), LocalDate.parse(addedOrder.getOrderDate()));
    }

    @Test
    void testUpdateOrder() throws FlooringPersistenceException {
        // Add an order
        Order testOrder = new Order(LocalDate.now().plusDays(1).toString(), "Test Update Customer", "TX",
                new BigDecimal("4.25"), "Wood", new BigDecimal("200"),
                new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("1030.00"), new BigDecimal("950.00"),
                new BigDecimal("85.08"), new BigDecimal("2065.08"));
        Order addedOrder = orderDao.addOrder(testOrder);

        // Update some fields of the order
        addedOrder.setCustomerName("Updated Customer");
        addedOrder.setArea(new BigDecimal("250"));

        // Update the order in the database
        Order updatedOrder = orderDao.updateOrder(addedOrder);

        // Verify the updated information is correct
        Order retrievedOrder = orderDao.getOrder(updatedOrder.getOrderNumber(), LocalDate.parse(updatedOrder.getOrderDate()));
        assertNotNull(retrievedOrder, "The retrieved order should not be null after update");
        assertEquals("Updated Customer", retrievedOrder.getCustomerName(), "Customer name should be updated");
        assertEquals(new BigDecimal("250.00"), retrievedOrder.getArea(), "Area should be updated");

        // Clean up
        orderDao.removeOrder(addedOrder.getOrderNumber(), LocalDate.parse(addedOrder.getOrderDate()));
    }

    @Test
    void testDeleteOrder() throws FlooringPersistenceException {
        // Add an order
        Order testOrder = new Order(LocalDate.now().plusDays(1).toString(), "Test Delete Customer", "TX",
                new BigDecimal("4.25"), "Wood", new BigDecimal("100"),
                new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("515.00"), new BigDecimal("475.00"),
                new BigDecimal("42.04"), new BigDecimal("1032.04"));
        Order addedOrder = orderDao.addOrder(testOrder);

        // Delete the order
        Order deletedOrder = orderDao.removeOrder(addedOrder.getOrderNumber(), LocalDate.parse(addedOrder.getOrderDate()));

        // Check that deletedOrder is not null and try to retrieve the deleted order
        assertNotNull(deletedOrder, "The deleted order should not be null");

        // Try to retrieve the deleted order and expect an exception or a null return value
        FlooringPersistenceException thrown = assertThrows(FlooringPersistenceException.class, () -> {
            orderDao.getOrder(deletedOrder.getOrderNumber(), LocalDate.parse(deletedOrder.getOrderDate()));
        }, "Expected an FlooringPersistenceException to be thrown when retrieving a deleted order");

        // also check the message of the exception if DAO method throws a specific message for not found entities
        assertTrue(thrown.getMessage().contains("Order not found"), "The exception message should indicate that the order was not found.");
    }

    @Test
    void testGetOrdersByDate() throws FlooringPersistenceException  {
        // Add two orders on the same date
        Order firstOrder = new Order(LocalDate.now().plusDays(2).toString(), "First Order Customer", "CA",
                new BigDecimal("3.50"), "Tile", new BigDecimal("150"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("525.00"), new BigDecimal("622.50"),
                new BigDecimal("49.05"), new BigDecimal("1196.55"));
        orderDao.addOrder(firstOrder);

        Order secondOrder = new Order(LocalDate.now().plusDays(2).toString(), "Second Order Customer", "TX",
                new BigDecimal("4.00"), "Wood", new BigDecimal("100"),
                new BigDecimal("1.75"), new BigDecimal("2.10"), new BigDecimal("175.00"), new BigDecimal("210.00"),
                new BigDecimal("16.45"), new BigDecimal("401.45"));
        orderDao.addOrder(secondOrder);

        // Retrieve orders for the specific date
        List<Order> orders = orderDao.getOrdersByDate(LocalDate.now().plusDays(2));

        // Assert
        assertNotNull(orders, "The list of orders should not be null");
        assertTrue(orders.size() >= 2, "There should be at least two orders for the specified date");

        // Clean up
        orders.forEach(order -> {
            try {
                orderDao.removeOrder(order.getOrderNumber(), LocalDate.parse(order.getOrderDate()));
            } catch (FlooringPersistenceException e) {
                throw new RuntimeException(e);
            }
        });


    }











}
