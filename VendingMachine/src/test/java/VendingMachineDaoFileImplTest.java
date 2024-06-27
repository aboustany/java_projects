
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.VendingMachineDaoFileImpl;
import VendingMachine.dto.MachineItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class VendingMachineDaoFileImplTest {
    private VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "test-vending-machine.txt";
        // Use try-with-resources to ensure the writer is closed properly
        try (FileWriter fw = new FileWriter(testFile, false)) {
            fw.write(""); // Clear the file content
        }
        testDao = new VendingMachineDaoFileImpl(testFile);
    }


    @Test
    void testAddAndGetItem() throws Exception {
        // Arrange
        MachineItem newItem = new MachineItem("Coke", new BigDecimal("1.50"), 10);
        testDao.addMachineItem(newItem);

        String itemName = "Coke";

        // Act
        MachineItem item = testDao.getMachineItem(itemName);

        // Assert
        assertNotNull(item, "The item should not be null");
        assertEquals(itemName, item.getName(), "The item name should match");
    }

    @Test
    void testUpdateInventory() throws Exception {
        // Arrange
        MachineItem item = new MachineItem("Pepsi", new BigDecimal("1.50"), 10);

        // Act
        testDao.updateMachineItem(item, item.getQuantity() - 1);
        MachineItem updatedItem = testDao.getMachineItem(item.getName());

        // Assert
        assertEquals(9, updatedItem.getQuantity(), "The inventory should be decremented");
    }

    @Test
    void testSellItemAndInventoryAdjustment() throws Exception {
        // Arrange
        MachineItem item = new MachineItem("Sprite", new BigDecimal("1.25"), 1);
        testDao.addMachineItem(item);

        // Act - Simulate selling the item, thus reducing its quantity to 0
        testDao.updateMachineItem(item, item.getQuantity() - 1);
        MachineItem updatedItem = testDao.getMachineItem(item.getName());

        // Assert - The item should still exist but with 0 quantity
        assertEquals(0, updatedItem.getQuantity(), "The inventory for Sprite should be 0 after sale.");
    }

    @Test
    void testGetAllItems() throws Exception {
        // Arrange - Add multiple items
        MachineItem firstItem = new MachineItem("Water", new BigDecimal("1.00"), 10);
        MachineItem secondItem = new MachineItem("Chips", new BigDecimal("0.75"), 0); // Assume sold out
        testDao.addMachineItem(firstItem);
        testDao.addMachineItem(secondItem);

        // Act
        List<MachineItem> allItems = testDao.getAllMachineItems();

        // Assert
        assertNotNull(allItems, "The list of items must not be null.");
        assertEquals(2, allItems.size(), "The list of items should include both items.");
        assertTrue(allItems.contains(firstItem), "The list should include Water.");
        assertTrue(allItems.contains(secondItem), "The list should include Chips, even if sold out.");
    }






}
