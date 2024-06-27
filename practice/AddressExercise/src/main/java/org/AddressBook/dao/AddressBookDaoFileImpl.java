package org.AddressBook.dao;

import org.AddressBook.dto.Address;
import org.AddressBook.exceptions.AddressBookDaoException;

import java.io.*;
import java.util.*;

public class AddressBookDaoFileImpl implements AddressBookDao {

    public static final String ROSTER_FILE = "addressBook.txt";
    public static final String DELIMITER = "::";
    private Map<String, Address> addressMap = new HashMap<>();
    @Override
    public Address addAddress(String lastName, Address address) throws AddressBookDaoException {
        loadRoster();
        Address newAddress = addressMap.put(lastName, address);
        writeRoster();
        return newAddress;
    }

    @Override
    public Address findAddress(String lastName) throws AddressBookDaoException {
        loadRoster();
        return addressMap.get(lastName);
    }

    @Override
    public List<Address> getAllAddresses() throws AddressBookDaoException {
        loadRoster();
        return new ArrayList<>(addressMap.values()) ;
    }

    @Override
    public int getAddressCount() throws AddressBookDaoException {
        return getAllAddresses().size();
    }

    @Override
    public Address deleteAddress(String lastName) throws AddressBookDaoException {
        loadRoster();
        Address deletedAddress = addressMap.remove(lastName);
        writeRoster();
        return deletedAddress;
    }


    private Address unmarshallAddress(String addressAsText){

        String[] addressTokens = addressAsText.split(DELIMITER);


        String firstName = addressTokens[0];
        String lastName = addressTokens[1];
        String address = addressTokens[2];

        return new Address(firstName, lastName, address);
    }

    private void loadRoster() throws AddressBookDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)));
        } catch (FileNotFoundException e) {
            throw new AddressBookDaoException(
                    "-_- Could not load roster data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;

        Address currentAddress;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentAddress = unmarshallAddress(currentLine);


            addressMap.put(currentAddress.getLastName(), currentAddress);
        }
        // close scanner
        scanner.close();
    }

    private String marshallAddress(Address anAddress) {

        String studentAsText = anAddress.getFirstName() + DELIMITER;


        studentAsText += anAddress.getLastName() + DELIMITER;


        studentAsText += anAddress.getStreetAddress();

        return studentAsText;
    }


    private void writeRoster() throws AddressBookDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            throw new AddressBookDaoException(
                    "Could not save student data.", e);
        }


        String addressAsText;
        List<Address> addressList = this.getAllAddresses();
        for (Address currentAddress : addressList) {

            addressAsText = marshallAddress(currentAddress);

            out.println(addressAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }
}
