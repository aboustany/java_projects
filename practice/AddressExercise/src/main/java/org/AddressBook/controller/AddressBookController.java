package org.AddressBook.controller;

import org.AddressBook.dao.AddressBookDao;
import org.AddressBook.dto.Address;
import org.AddressBook.exceptions.AddressBookDaoException;
import org.AddressBook.ui.AddressBookView;
import org.AddressBook.ui.UserIO;
import org.AddressBook.ui.UserIOConsoleImpl;

import java.util.ArrayList;
import java.util.List;

public class AddressBookController {

    private AddressBookView view;

    private AddressBookDao dao;
    private UserIO io = new UserIOConsoleImpl();

    public AddressBookController(AddressBookDao dao, AddressBookView view){
        this.dao = dao;
        this.view = view;
    }

    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        try{
            while(keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        io.print("ADD ADDRESS");
                        addAddress();
                        break;
                    case 2:
                        io.print("DELETE ADDRESS");
                        deleteAddress();
                        break;
                    case 3:
                        io.print("FIND ADDRESS");
                        findAddress();
                        break;
                    case 4:
                        io.print("LIST ADDRESS COUNT");
                        listAddressCount();
                        break;
                    case 5:
                        io.print("LIST ALL ADDRESSES");
                        listAllAddresses();
                        break;
                    case 0:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                        break;
                }
            }
            exitMessage();
        } catch (AddressBookDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addAddress() throws AddressBookDaoException{
        view.displayCreateAddressBanner();
        Address newAddress = view.getNewAddressInfo();
        dao.addAddress(newAddress.getLastName(), newAddress);
        view.displayCreateAddressSuccessBanner();
    }

    private void deleteAddress() throws AddressBookDaoException{
        view.displayDeleteAddressBanner();
        String lastName = view.getLastName();
        Address deletedAddress = dao.deleteAddress(lastName);
        view.displayDeletedAddress(deletedAddress);
    }

    private void findAddress() throws AddressBookDaoException{
        view.displayFindAddressBanner();
        String lastName = view.getLastName();
        Address foundAddress = dao.findAddress(lastName);
        view.displayFoundAddress(foundAddress);
    }

    private void listAddressCount() throws AddressBookDaoException{
        view.displayAddressCountBanner();
        int count = dao.getAddressCount();
        view.displayAddressCount(count);
    }

    private void listAllAddresses() throws AddressBookDaoException{
        view.displayAllAddressesBanner();
        List<Address> allAddresses = dao.getAllAddresses();
        view.displayAllAddresses(allAddresses);
    }

    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }



}
