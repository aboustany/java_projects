package UnitTesting;

import FlooringMastery.FlooringMasteryTestConfig;
import FlooringMastery.dao.OrderDao;
import FlooringMastery.dao.ProductDao;
import FlooringMastery.dao.TaxDao;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.exceptions.FlooringPersistenceException;
import FlooringMastery.exceptions.FlooringUserInvalidDataException;
import FlooringMastery.service.FlooringServiceLayerImpl;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FlooringMasteryTestConfig.class)
public class FlooringServiceLayerImplTest {
    @Mock
    private OrderDao orderDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private TaxDao taxDao;

    @InjectMocks
    private FlooringServiceLayerImpl service;


    @BeforeEach
    void setUp() throws FlooringPersistenceException {
        // Setup mock responses for the DAOs
        when(productDao.getProductByType(anyString())).thenReturn(new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15")));
        when(taxDao.getTaxByState(anyString())).thenReturn(new Tax("TX", "Texas", new BigDecimal("4.45")));
    }

    @Test
    void addOrder_ValidOrder_ShouldCalculateCostsAndAddOrder() throws FlooringPersistenceException, FlooringUserInvalidDataException {
        // Given a valid order
        String orderDate = LocalDate.now().plusDays(1).toString();
        String customerName = "John Doe";
        String state = "TX";
        String productType = "Tile";
        BigDecimal area = new BigDecimal("100");

        Order order = new Order(orderDate, customerName, state, productType, area);

        // adding the order
        service.addOrder(order);

        // Then calculateOrderCosts should be called and the order should be added
        verify(orderDao).addOrder(order);
        assertNotNull(order.getMaterialCost());
        assertNotNull(order.getLaborCost());
        assertNotNull(order.getTax());
        assertNotNull(order.getTotal());
    }

    @Test
    void getOrdersByDate_ValidDate_ShouldReturnOrders() throws FlooringPersistenceException {
        // Given a valid date and setup mock response
        LocalDate date = LocalDate.now();
        when(orderDao.getOrdersByDate(date)).thenReturn(Arrays.asList(
                new Order(LocalDate.now().toString(),"John", "TX", "Tile", new BigDecimal("177")),
                new Order(LocalDate.now().toString(),"Thalia", "CA", "Wood", new BigDecimal("194"))));

        // When retrieving orders by date
        List<Order> orders = service.getOrdersByDate(date);

        // Then the result should not be empty
        assertFalse(orders.isEmpty());
    }

    @Test
    void updateOrder_ValidOrder_ShouldUpdateOrder() throws FlooringPersistenceException, FlooringUserInvalidDataException {
        // Given a valid order to update
        Order order = new Order(LocalDate.now().plusDays(1).toString(), "Jane Doe", "CA", "Wood", new BigDecimal("150"));
        order.setOrderNumber(1);

        // Setup stubbing for DAOs
        when(orderDao.getOrder(order.getOrderNumber(), LocalDate.parse(order.getOrderDate()))).thenReturn(order);
        doNothing().when(orderDao).updateOrder(any(Order.class));

        // When updating the order
        Order updatedOrder = service.updateOrder(order);

        // Then validate the update process
        assertNotNull(updatedOrder);
        verify(orderDao).updateOrder(order);
    }

    @Test
    void removeOrder_ExistingOrder_ShouldRemoveOrder() throws FlooringPersistenceException {
        // Given an existing order
        int orderNumber = 1;
        LocalDate date = LocalDate.now().plusDays(1);

        Order order = new Order(date.toString(), "Jane Doe", "CA", "Wood", new BigDecimal("150"));
        order.setOrderNumber(orderNumber);

        when(orderDao.removeOrder(orderNumber, date)).thenReturn(order);

        // When removing the order
        Order removedOrder = service.removeOrder(orderNumber, date);

        // Then the order should be removed successfully
        assertNotNull(removedOrder, "Removed order should not be null");
        verify(orderDao).removeOrder(orderNumber, date);
    }

    @Test
    void validateOrderData_PastDate_ShouldThrowException() {
        // Variables for order with a past order date
        String orderDate = LocalDate.now().minusDays(1).toString();
        String customerName = "John Doe";
        String state = "TX";
        String productType = "Tile";
        BigDecimal area = new BigDecimal("200");

        // Create order with past date
        Order order = new Order(orderDate, customerName, state, productType, area);

        // When validating order data
        Exception exception = assertThrows(FlooringUserInvalidDataException.class, () -> service.validateOrderData(order));

        // Then an exception for past order date should be thrown
        assertTrue(exception.getMessage().contains("Order date must be in the future"), "Expected exception for past order date not thrown.");
    }

    @Test
    void validateOrderData_EmptyName_ShouldThrowException() {
        // Variables for order with an empty customer name
        String orderDate = LocalDate.now().plusDays(1).toString();
        String customerName = ""; // Empty name
        String state = "TX";
        String productType = "Tile";
        BigDecimal area = new BigDecimal("200");

        // Create order with empty name
        Order order = new Order(orderDate, customerName, state, productType, area);

        // When validating order data
        Exception exception = assertThrows(FlooringUserInvalidDataException.class, () -> service.validateOrderData(order));

        // Then an exception for empty customer name should be thrown
        assertTrue(exception.getMessage().contains("Customer name cannot be blank"), "Expected exception for empty customer name not thrown.");
    }

    @Test
    void validateOrderData_AreaBelowMinimum_ShouldThrowException() {
        // Variables for order with area below the minimum requirement
        String orderDate = LocalDate.now().plusDays(1).toString();
        String customerName = "John Doe";
        String state = "TX";
        String productType = "Tile";
        BigDecimal area = new BigDecimal("99"); // Area below minimum

        // Create order with area below minimum
        Order order = new Order(orderDate, customerName, state, productType, area);

        // When validating order data
        Exception exception = assertThrows(FlooringUserInvalidDataException.class, () -> service.validateOrderData(order));

        // Then an exception for area below minimum should be thrown
        assertTrue(exception.getMessage().contains("Minimum order size is 100 sq ft"), "Expected exception for area below minimum not thrown.");
    }



    @Test
    void calculateOrderCosts_ValidOrder_ShouldCalculateCosts() throws FlooringPersistenceException {
        // Given a valid order
        Order order = new Order(LocalDate.now().plusDays(1).toString(), "John Doe", "TX", "Tile", new BigDecimal("100"));

        // When calculating order costs
        service.calculateOrderCosts(order);

        // Then all costs should be calculated and set
        assertEquals(new BigDecimal("4.45"), order.getTaxRate(), "Tax rate should match");
        assertEquals(new BigDecimal("3.50"), order.getCostPerSquareFoot(), "Cost per square foot should match");
        assertEquals(new BigDecimal("4.15"), order.getLaborCostPerSquareFoot(), "Labor cost per square foot should match");
        assertEquals(new BigDecimal("350.00"), order.getMaterialCost(), "Material cost should match expected value");
        assertEquals(new BigDecimal("415.00"), order.getLaborCost(), "Labor cost should match expected value");
        assertEquals(new BigDecimal("34.04"), order.getTax(), "Tax should match expected value");
        assertEquals(new BigDecimal("799.04"), order.getTotal(), "Total should match expected value");
    }

    @Test
    void updateOrder_ChangeProductType_ShouldRecalculateCosts() throws FlooringPersistenceException, FlooringUserInvalidDataException {
        // Given an existing order
        Order originalOrder = new Order(LocalDate.now().plusDays(1).toString(), "John Doe", "TX", "Tile", new BigDecimal("100"));
        originalOrder.setOrderNumber(1);
        when(orderDao.getOrder(1, LocalDate.parse(originalOrder.getOrderDate()))).thenReturn(originalOrder);

        // When changing the product type from Tile to Wood which has a different cost
        Order updatedOrder = new Order(originalOrder.getOrderDate(), originalOrder.getCustomerName(), originalOrder.getState(), "Wood", originalOrder.getArea());
        updatedOrder.setOrderNumber(1);


        when(productDao.getProductByType("Wood")).thenReturn(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));

        service.updateOrder(updatedOrder);

        // Then ensure the costs were recalculated and updated in the database
        assertNotEquals(originalOrder.getMaterialCost(), updatedOrder.getMaterialCost(), "Material cost should be recalculated");
        assertNotEquals(originalOrder.getTotal(), updatedOrder.getTotal(), "Total cost should be recalculated");
        verify(orderDao).updateOrder(any(Order.class));
    }



}
