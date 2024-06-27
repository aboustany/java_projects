package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import FlooringMastery.exceptions.FlooringPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
@Repository
public class OrderDaoDbImpl implements OrderDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoDbImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Order addOrder(Order order) throws FlooringPersistenceException {
        final String INSERT_ORDER = "INSERT INTO Orders "
                + "(orderDate, customerName, state, productType, area, materialCost, laborCost, tax, total) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_ORDER, new String[]{"orderNumber"});
            ps.setDate(1, Date.valueOf(LocalDate.parse(order.getOrderDate())));
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getState());
            ps.setString(4, order.getProductType());
            ps.setBigDecimal(5, order.getArea());
            ps.setBigDecimal(6, order.getMaterialCost());
            ps.setBigDecimal(7, order.getLaborCost());
            ps.setBigDecimal(8, order.getTax());
            ps.setBigDecimal(9, order.getTotal());
            return ps;
        }, keyHolder);

        // Setting the order number from the generated key
        if (keyHolder.getKeys().size() > 1) {
            order.setOrderNumber((Integer) keyHolder.getKeys().get("orderNumber")); // Use this if returning multiple keys
        } else {
            order.setOrderNumber(keyHolder.getKey().intValue()); // Use this for a single key
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException {
        final String SELECT_ORDERS_BY_DATE = "SELECT * FROM Orders WHERE orderDate = ?";
        List<Order> orders = jdbcTemplate.query(SELECT_ORDERS_BY_DATE, new OrderMapper(), Date.valueOf(date));
        if(orders.isEmpty()){
            throw new FlooringPersistenceException("No orders found for date: " + date);
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException {
        final String SELECT_ORDER = "SELECT * FROM Orders WHERE orderNumber = ? AND orderDate = ?";
        List<Order> orders = jdbcTemplate.query(SELECT_ORDER, new OrderMapper(), orderNumber, Date.valueOf(date));
        if (orders.isEmpty()) {
            throw new FlooringPersistenceException("Order not found.");
        }
        return orders.get(0);
    }


    @Override
    public Order updateOrder(Order order) throws FlooringPersistenceException {
        final String UPDATE_ORDER = "UPDATE Orders SET "
                + "customerName = ?, state = ?, productType = ?, area = ?, materialCost = ?, laborCost = ?, tax = ?, total = ? "
                + "WHERE orderNumber = ? AND orderDate = ?";
        jdbcTemplate.update(UPDATE_ORDER,
                order.getCustomerName(),
                order.getState(),
                order.getProductType(),
                order.getArea(),
                order.getMaterialCost(),
                order.getLaborCost(),
                order.getTax(),
                order.getTotal(),
                order.getOrderNumber(),
                LocalDate.parse(order.getOrderDate()));
        return order;
    }

    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException {
        Order orderToRemove = getOrder(orderNumber, date);
        if(orderToRemove != null) {
            final String DELETE_ORDER = "DELETE FROM Orders WHERE orderNumber = ? AND orderDate = ?";
            jdbcTemplate.update(DELETE_ORDER, orderNumber, Date.valueOf(date));
            return orderToRemove;
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        final String SELECT_ALL_ORDERS_WITH_DETAILS = "SELECT o.orderNumber, o.orderDate, o.customerName, o.state, t.taxRate, o.productType, " +
                "p.costPerSquareFoot, p.laborCostPerSquareFoot, o.area, o.materialCost, o.laborCost, o.tax, o.total " +
                "FROM Orders o " +
                "JOIN Products p ON o.productType = p.productType " +
                "JOIN Taxes t ON o.state = t.stateAbbreviation";
        return jdbcTemplate.query(SELECT_ALL_ORDERS_WITH_DETAILS, new OrderMapperWithDetails());
    }


    private static class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            int orderNumber = rs.getInt("orderNumber");
            String orderDate = rs.getDate("orderDate").toString();
            String customerName = rs.getString("customerName");
            String state = rs.getString("state");
            String productType = rs.getString("productType");
            BigDecimal area = rs.getBigDecimal("area");
            BigDecimal materialCost = rs.getBigDecimal("materialCost");
            BigDecimal laborCost = rs.getBigDecimal("laborCost");
            BigDecimal tax = rs.getBigDecimal("tax");
            BigDecimal total = rs.getBigDecimal("total");
            Order newOrderEntry = new Order(orderDate, customerName, state, productType, area, materialCost, laborCost, tax, total);
            newOrderEntry.setOrderNumber(orderNumber);
            return newOrderEntry;
        }
    }

    private static class OrderMapperWithDetails implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            int orderNumber = rs.getInt("orderNumber");
            String orderDate = rs.getDate("orderDate").toLocalDate().toString();
            String customerName = rs.getString("customerName");
            String state = rs.getString("state");
            BigDecimal taxRate = rs.getBigDecimal("taxRate");
            String productType = rs.getString("productType");
            BigDecimal area = rs.getBigDecimal("area");
            BigDecimal costPerSquareFoot = rs.getBigDecimal("costPerSquareFoot");
            BigDecimal laborCostPerSquareFoot = rs.getBigDecimal("laborCostPerSquareFoot");
            BigDecimal materialCost = rs.getBigDecimal("materialCost");
            BigDecimal laborCost = rs.getBigDecimal("laborCost");
            BigDecimal tax = rs.getBigDecimal("tax");
            BigDecimal total = rs.getBigDecimal("total");
            Order order = new Order(orderDate, customerName, state, taxRate, productType, area,
                    costPerSquareFoot, laborCostPerSquareFoot, materialCost,
                    laborCost, tax, total);
            order.setOrderNumber(orderNumber);
            return order;
        }
    }




}
