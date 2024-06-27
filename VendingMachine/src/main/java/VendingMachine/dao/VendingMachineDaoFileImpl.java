package VendingMachine.dao;

import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachinePersistenceException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Component
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    public VendingMachineDaoFileImpl(){
        VENDING_MACHINE_FILE = "vendingmachine.txt";
    }

    public VendingMachineDaoFileImpl(String file){
        VENDING_MACHINE_FILE = file;
    }
    private final String VENDING_MACHINE_FILE;
    public static final String DELIMITER = "::";
    private Map<String, MachineItem> items = new HashMap<>();

    @Override
    public MachineItem addMachineItem(MachineItem item) throws VendingMachinePersistenceException {
        loadItems();
        MachineItem newItem = items.put(item.getName(), item);
        writeItems();
        return newItem;
    }

    @Override
    public MachineItem getMachineItem(String name) throws VendingMachinePersistenceException {
        loadItems();
        return items.get(name);
    }

    @Override
    public List<MachineItem> getAllMachineItems() throws VendingMachinePersistenceException {
        loadItems();
        return new ArrayList<>(items.values());
    }


    @Override
    public MachineItem updateMachineItem(MachineItem item, int quantity) throws VendingMachinePersistenceException {
        loadItems();
        item.setQuantity(quantity);
        items.put(item.getName(), item);
        writeItems();
        return item;
    }

    @Override
    public MachineItem deleteMachineItem(String name) throws VendingMachinePersistenceException {
        loadItems();
        MachineItem item = items.remove(name);
        writeItems();
        return item;
    }

    public MachineItem unmarshallMachineItem(String itemAsString) {
        String[] itemTokens = itemAsString.split(DELIMITER);
        String name = itemTokens[0];
        BigDecimal price = new BigDecimal(itemTokens[1]);
        int quantity = Integer.parseInt(itemTokens[2]);
        MachineItem item = new MachineItem(name, price, quantity);
        return item;
    }

    public String marshallMachineItem(MachineItem item) {
        String itemAsText = item.getName() + DELIMITER;
        itemAsText += item.getPrice() + DELIMITER;
        itemAsText += item.getQuantity();
        return itemAsText;
    }


    public void loadItems() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;

        MachineItem currentItem;


        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallMachineItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }


    public void writeItems() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save student data.", e);
        }

        String itemAsText;
        List<MachineItem> itemList = this.getAllMachineItems();
        for (MachineItem item : itemList) {
            itemAsText = marshallMachineItem(item);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }


}
