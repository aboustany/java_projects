package FlooringMastery.service;

import FlooringMastery.dao.OrderDao;
import FlooringMastery.dao.ProductDao;
import FlooringMastery.dao.TaxDao;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.exceptions.FlooringPersistenceException;
import FlooringMastery.exceptions.FlooringUserInvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
@Component
public class FlooringServiceLayerImpl implements FlooringServiceLayer {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxDao taxDao;

    @Autowired
    public FlooringServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public Order addOrder(Order order) throws FlooringPersistenceException, FlooringUserInvalidDataException {
        validateOrderData(order);
        calculateOrderCosts(order);
        return orderDao.addOrder(order);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException {
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException {
        Order retrievedOrder = orderDao.getOrder(orderNumber, date);
        if (retrievedOrder == null) {
            throw new FlooringPersistenceException("Order not found.");
        }
        calculateOrderCosts(retrievedOrder);
        return retrievedOrder;
    }

    @Override
    public Order updateOrder(Order order) throws FlooringPersistenceException, FlooringUserInvalidDataException {
        validateOrderData(order);
        calculateOrderCosts(order);
        return orderDao.updateOrder(order);
    }

    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringPersistenceException {
        return orderDao.removeOrder(orderNumber, date);
    }

    @Override
    public void validateOrderData(Order order) throws FlooringUserInvalidDataException, FlooringPersistenceException {
        if(order.getOrderDate().compareTo(String.valueOf(LocalDate.now())) <= 0){
            throw new FlooringUserInvalidDataException("Order date must be in the future");
        }
        if (order.getCustomerName() == null || order.getCustomerName().trim().isEmpty()) {
            throw new FlooringUserInvalidDataException("Customer name cannot be blank.");
        }
        if (order.getArea().compareTo(new BigDecimal(100)) < 0) {
            throw new FlooringUserInvalidDataException("Minimum order size is 100 sq ft.");
        }
        // Validate state exists in tax info
        Tax tax = taxDao.getTaxByState(order.getState());
        if (tax == null) {
            throw new FlooringUserInvalidDataException("The state does not exist in the tax file.");
        }
        // Validate product type exists
        Product product = productDao.getProductByType(order.getProductType());
        if (product == null) {
            throw new FlooringUserInvalidDataException("The product type does not exist.");
        }
    }

    @Override
    public void calculateOrderCosts(Order order) throws FlooringPersistenceException {
        // Fetch tax rate
        Tax tax = taxDao.getTaxByState(order.getState());
        BigDecimal taxRate = tax.getTaxRate();
        order.setTaxRate(taxRate);

        // Fetch product costs
        Product product = productDao.getProductByType(order.getProductType());
        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();

        order.setCostPerSquareFoot(costPerSquareFoot);
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        // Calculate costs
        BigDecimal materialCost = costPerSquareFoot.multiply(order.getArea());
        BigDecimal laborCost = laborCostPerSquareFoot.multiply(order.getArea());
        BigDecimal subtotal = materialCost.add(laborCost);
        BigDecimal taxAmount = subtotal.multiply(taxRate.divide(new BigDecimal("100")))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(taxAmount);

        // Set calculated costs on order
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(taxAmount);
        order.setTotal(total);
    }

    @Override
    public List<Product> getProducts() throws FlooringPersistenceException {
        return productDao.getProducts();
    }

    @Override
    public List<Tax> getTaxes() throws FlooringPersistenceException {
        return taxDao.getTaxes();
    }

    @Override
    public void exportAllData() throws FlooringPersistenceException {
        List<Order> allOrders = orderDao.getAllOrders();
        if (allOrders.isEmpty()) {
            throw new FlooringPersistenceException("No orders available to export.");
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("DataExport.txt"))) {
            for (Order order : allOrders) {
                out.println(order.getOrderNumber() + "," + order.getCustomerName() + "," + order.getState() + "," +
                        order.getTaxRate() + "," + order.getProductType() + "," + order.getArea() + "," +
                        order.getCostPerSquareFoot() + "," + order.getLaborCostPerSquareFoot() + "," +
                        order.getMaterialCost() + "," + order.getLaborCost() + "," + order.getTax() + "," +
                        order.getTotal() + "," + order.getOrderDate());
            }
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not export data.", e);
        }
    }


}
