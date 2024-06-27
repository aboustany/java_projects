package VendingMachine.service;

import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachinePersistenceException;
import VendingMachine.exceptions.VendingMachineInsufficientFundsException;
import VendingMachine.exceptions.VendingMachineNoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {
    /**
     * Purchase an item with the given name using the specified payment.
     *
     * @param  itemName  the name of the item to purchase
     * @param  payment   the amount of payment for the item
     * @return           the total amount of change returned to the user
     * @throws VendingMachinePersistenceException        if there is an error persisting the transaction
     * @throws VendingMachineNoItemInventoryException   if there is no inventory of the item available
     * @throws VendingMachineInsufficientFundsException if the payment is less than the price of the item
     */
    BigDecimal purchaseItem(String itemName, BigDecimal payment) throws VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException, VendingMachineInsufficientFundsException, VendingMachinePersistenceException;

    List<MachineItem> getAllMachineItems() throws VendingMachinePersistenceException;

}
