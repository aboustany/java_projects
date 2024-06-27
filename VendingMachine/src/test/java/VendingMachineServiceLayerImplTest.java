import VendingMachine.dao.AuditDao;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachineInsufficientFundsException;
import VendingMachine.exceptions.VendingMachineNoItemInventoryException;
import VendingMachine.exceptions.VendingMachinePersistenceException;
import VendingMachine.service.VendingMachineServiceLayerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayerImpl service;

    @Mock
    private VendingMachineDao daoMock;
    @Mock
    private AuditDao auditDaoMock;

    public VendingMachineServiceLayerImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        service = new VendingMachineServiceLayerImpl(daoMock, auditDaoMock);
    }

    @Test
    @DisplayName("Purchase item with exact change")
    public void testPurchaseItemExactChange() throws Exception {
        String itemName = "Coke";
        BigDecimal payment = new BigDecimal("1.50");
        MachineItem item = new MachineItem(itemName, payment, 10);

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        BigDecimal change = service.purchaseItem(itemName, payment);

        // Use argThat to match MachineItem with expected properties
        verify(daoMock).updateMachineItem(argThat(updatedItem ->
                updatedItem.getName().equals(itemName) &&
                        updatedItem.getQuantity() == 9
        ), anyInt());
        assertEquals(new BigDecimal("0.00"), change, "Change should be zero for exact payment.");
    }


    @Test
    @DisplayName("Purchase item with insufficient funds")
    public void testPurchaseItemInsufficientFunds() throws VendingMachinePersistenceException {

        String itemName = "Pepsi";
        BigDecimal payment = new BigDecimal("1.00");
        MachineItem item = new MachineItem(itemName, new BigDecimal("1.50"), 10);

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        assertThrows(VendingMachineInsufficientFundsException.class, () -> service.purchaseItem(itemName, payment),
                "Should throw InsufficientFundsException for insufficient payment.");
    }

    @Test
    @DisplayName("Purchase item out of stock")
    public void testPurchaseItemOutOfStock() throws VendingMachinePersistenceException {

        String itemName = "Sprite";
        MachineItem item = new MachineItem(itemName, new BigDecimal("1.00"), 0); // Out of stock

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        assertThrows(VendingMachineNoItemInventoryException.class, () -> service.purchaseItem(itemName, new BigDecimal("1.00")),
                "Should throw NoItemInventoryException for items out of stock.");
    }

    @Test
    @DisplayName("Get all items")
    public void testGetAllItems() throws Exception {
        MachineItem item1 = new MachineItem("Water", new BigDecimal("1.00"), 10);
        MachineItem item2 = new MachineItem("Chips", new BigDecimal("0.75"), 5);

        when(daoMock.getAllMachineItems()).thenReturn(Arrays.asList(item1, item2));

        List<MachineItem> items = service.getAllMachineItems();

        assertNotNull(items, "The list of items should not be null");
        assertEquals(2, items.size(), "Should return all items.");
        assertTrue(items.contains(item1) && items.contains(item2), "The list should contain both items.");
    }

    @Test
    @DisplayName("Purchase item with more than exact change")
    public void testPurchaseItemWithMoreChange() throws Exception {

        String itemName = "Water";
        BigDecimal payment = new BigDecimal("2.00"); // More than the item price
        MachineItem item = new MachineItem(itemName, new BigDecimal("1.50"), 10);

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        BigDecimal change = service.purchaseItem(itemName, payment);

        assertEquals(new BigDecimal("0.50"), change, "Change should be correctly calculated.");
    }

    @Test
    @DisplayName("Verify successful purchase is logged")
    public void testSuccessfulPurchaseIsLogged() throws Exception {

        String itemName = "Chips";
        BigDecimal payment = new BigDecimal("1.00");
        MachineItem item = new MachineItem(itemName, new BigDecimal("0.75"), 10);

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        service.purchaseItem(itemName, payment);

        verify(auditDaoMock).writeAuditEntry(contains("Transaction successful for item: " + itemName));
    }

    @Test
    @DisplayName("Verify insufficient funds exception is logged")
    public void testInsufficientFundsExceptionIsLogged() throws VendingMachinePersistenceException {

        String itemName = "Pepsi";
        BigDecimal payment = new BigDecimal("0.50");
        MachineItem item = new MachineItem(itemName, new BigDecimal("1.50"), 10);

        when(daoMock.getMachineItem(itemName)).thenReturn(item);

        assertThrows(VendingMachineInsufficientFundsException.class, () -> service.purchaseItem(itemName, payment));

        verify(auditDaoMock).writeAuditEntry(contains("Insufficient funds"));
    }


}
