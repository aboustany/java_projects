package VendingMachine.controller;

import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.Change;
import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachineInsufficientFundsException;
import VendingMachine.exceptions.VendingMachineNoItemInventoryException;
import VendingMachine.exceptions.VendingMachinePersistenceException;
import VendingMachine.service.VendingMachineServiceLayer;
import VendingMachine.ui.UserIO;
import VendingMachine.ui.UserIOConsoleImpl;
import VendingMachine.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller("controller")
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineController(VendingMachineView view,  VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    
    public void run() {
        boolean keepGoing = true;

        try{
            while (keepGoing) {
                welcomeMessage();
                listItems();
                selectItem();
                keepGoing = false;
            }
            exitMessage();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());

        }
    }


    private void welcomeMessage() {
        view.displayWelcomeBanner();
    }


    private void exitMessage() {
        System.out.println("Goodbye!");
    }


    private void selectItem() {
        try {
            BigDecimal payment = view.collectPayment();
            List<MachineItem> items = service.getAllMachineItems();
            int selectedItemIndex = view.getItemSelection(items.size());
            String selectedItemName = items.get(selectedItemIndex-1).getName();
            BigDecimal change = service.purchaseItem(selectedItemName, payment);
            Map<Change.Coin, Integer> changeCoins = Change.calculateChangeCoins(change);
            view.displaySuccessBanner(selectedItemName);
            view.displayChange(change);
            view.displayChangeCoins(changeCoins);
        } catch (VendingMachinePersistenceException |
                 VendingMachineInsufficientFundsException |
                 VendingMachineNoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }


    private void listItems() throws VendingMachinePersistenceException {
        view.displayAllItemsBanner();
        List<MachineItem> items = service.getAllMachineItems();
        view.displayItems(items);
    }


}
