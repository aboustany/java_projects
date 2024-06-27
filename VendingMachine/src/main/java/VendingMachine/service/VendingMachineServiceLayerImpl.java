package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.Change;
import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachinePersistenceException;
import VendingMachine.exceptions.VendingMachineInsufficientFundsException;
import VendingMachine.exceptions.VendingMachineNoItemInventoryException;
import VendingMachine.util.LoggingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static VendingMachine.util.LoggingUtility.auditDao;
@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private AuditDao auditDao;

    @Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, AuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public BigDecimal purchaseItem(String itemName, BigDecimal payment) throws VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException, VendingMachineInsufficientFundsException, VendingMachinePersistenceException {
        MachineItem item = dao.getMachineItem(itemName);

        if(item == null){
            throw new VendingMachinePersistenceException("Error fetching item.");
        }
        if(item.getQuantity() <= 0){
            String err = "Item out of stock.";
            LoggingUtility.log("Transaction failed for item: " + itemName + ", Payment: $" + payment + ", Reason: " + err,
                    auditDao::writeAuditEntry);
            throw new VendingMachineNoItemInventoryException("Item out of stock.");
        }
        if(item.getPrice().compareTo(payment) > 0){
            String err = "Insufficient funds.";
            LoggingUtility.log("Transaction failed for item: " + itemName + ", Payment: $" + payment + ", Reason: " + err,
                    auditDao::writeAuditEntry);
            throw new VendingMachineInsufficientFundsException(err);
        }
        item.setQuantity(item.getQuantity() - 1);
        dao.updateMachineItem(item, item.getQuantity());

        BigDecimal change = Change.getChange(item.getPrice(), payment);

        try{
            LoggingUtility.log("Transaction successful for item: " + itemName + ", Payment: $" + payment + ", Change: $" + change,
                    auditDao::writeAuditEntry);
        }
        catch(VendingMachinePersistenceException e){
            System.err.println("Failed to log transaction: " + e.getMessage());
        }

        return change;
    }

    @Override
    public List<MachineItem> getAllMachineItems() throws VendingMachinePersistenceException {
        return dao.getAllMachineItems();
    }


}
