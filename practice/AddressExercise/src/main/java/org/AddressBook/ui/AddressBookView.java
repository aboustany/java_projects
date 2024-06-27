package org.AddressBook.ui;

import org.AddressBook.dto.Address;

import java.util.List;

public class AddressBookView {

    private UserIO io;

    public AddressBookView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add Address");
        io.print("2. Delete Address");
        io.print("3. Find Address");
        io.print("4. List Address Count");
        io.print("5. List All Addresses");
        io.print("0. Exit");

        return io.readInt("Please select from the above choices.", 0, 5);
    }


    public void displayCreateAddressSuccessBanner() {
        io.readString("Address successfully created.  Please hit enter to continue.");
    }

    public void displayCreateAddressBanner() {
        io.print("=== Create Address ===");
    }

    public Address getNewAddressInfo() {
        String firstName = io.readString("Enter the first name");
        String lastName = io.readString("Enter the last name");
        String streetAddress = io.readString("Enter the street address");
        return new Address(firstName, lastName, streetAddress);
    }

    public void displayErrorMessage(String message) {
        io.print("=== ERROR ===");
        io.print(message);
    }

    public void displayDeleteAddressBanner() {
        io.print("=== Delete Address ===");
    }

    public String getLastName() {
        return io.readString("Enter the last name of the address:");
    }



    public void displayDeletedAddress(Address deletedAddress) {
        if(deletedAddress!=null){
            io.print("Address successfully deleted.");
            io.print(deletedAddress.getFullName());
            io.print(deletedAddress.getStreetAddress());
        }
        else {
            io.print("Address not found.");
        }
        io.readString("Please hit enter to continue.");

    }

    public void displayFindAddressBanner() {
        io.print("=== Find Address ===");
    }

    public void displayFoundAddress(Address foundAddress) {
        if(foundAddress!=null){
            io.print("Address found.");
            io.print(foundAddress.getFullName());
            io.print(foundAddress.getStreetAddress());
        }
        else {
            io.print("Address not found.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayAddressCountBanner() {
        io.print("=== Address Count ===");
    }

    public void displayAddressCount(int count) {
        io.print("There are " + count + " addresses in the address book.");
    }

    public void displayAllAddressesBanner() {
        io.print("=== List All Addresses ===");
    }

    public void displayAllAddresses(List<Address> allAddresses) {
        for (Address address : allAddresses) {
            io.print(address.getFullName());
            io.print(address.getStreetAddress());
            io.print("");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown command!");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
}

