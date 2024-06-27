package VendingMachine.dao;

import VendingMachine.dto.MachineItem;
import VendingMachine.exceptions.VendingMachinePersistenceException;

import java.util.List;

public interface VendingMachineDao {
     MachineItem addMachineItem(MachineItem item) throws VendingMachinePersistenceException;
     MachineItem getMachineItem(String name) throws VendingMachinePersistenceException;

     List<MachineItem> getAllMachineItems() throws VendingMachinePersistenceException;
     MachineItem updateMachineItem(MachineItem item, int quantity) throws VendingMachinePersistenceException;
     MachineItem deleteMachineItem(String name) throws VendingMachinePersistenceException;


}
