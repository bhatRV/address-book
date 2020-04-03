package com.pwc.address.book.service;


import com.pwc.address.book.entities.Contact;
import com.pwc.address.book.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;




/**
 * @author  Rashmi
 */
@Service
public class AddressBookService {

    private static final String ADDRESS_BOOK_EARTH = "address-book-Earth";
    private static final String ADDRESS_BOOK_MARS= "address-book-Mars";

    //TODO: should be replaced with appropriate persistence
    private final Map<String, List<Contact>> addressBooks = new HashMap<>();

    public AddressBookService() {
        //Pre-populate the Data
        addressBooks.put(ADDRESS_BOOK_EARTH, new ArrayList<>());
        addressBooks.put(ADDRESS_BOOK_MARS, new ArrayList<>());

        this.addContact(ADDRESS_BOOK_EARTH, new Contact("Shane", "Warne", Arrays.asList("0331445543")));
        this.addContact(ADDRESS_BOOK_EARTH, new Contact("Steve", "Smith", Arrays.asList("0331445545")));
        this.addContact(ADDRESS_BOOK_EARTH, new Contact("Ricky", "Ponting", Arrays.asList("03313411111")));


        this.addContact(ADDRESS_BOOK_MARS, new Contact("David", "Warner", Arrays.asList("0331345543")));
        this.addContact(ADDRESS_BOOK_MARS, new Contact("Ricky", "Ponting", Arrays.asList("03313411111")));
    }

    public Contact addContact(String addressBookId, Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        if (!addressBooks.containsKey(addressBookId)) {
            addressBooks.put(addressBookId, new ArrayList<>());
        }
        addressBooks.get(addressBookId).add(contact);
        return contact;
    }

    public Contact removeContact(String addressBookId, final String contactId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        Optional<Contact> contactToBeRemoved = addressBooks.get(addressBookId).stream()
                .filter(contact -> contact.getId().equals(contactId)).findFirst();
        if (contactToBeRemoved.isPresent()) {
            addressBooks.get(addressBookId).remove(contactToBeRemoved.get());
            return contactToBeRemoved.get();
        } else {
            throw new ValidationException("The given contact does not exist!");
        }
    }

    public List<Contact> retrieveContacts(String addressBookId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        return addressBooks.get(addressBookId);
    }

    public List<Contact> retrieveAllUniqueContacts(String condition) {
        List<Contact> contacts = new ArrayList<>();
        addressBooks.values().forEach(contacts::addAll);
        if (condition.equals("UNIQUE")) {
            return contacts.stream().distinct().collect(Collectors.toList());
        }
        else if (condition.equals("COMMON")) {
            return contacts.stream()
                    .filter(item -> Collections.frequency(contacts, item) >= 2).distinct()
                    .collect(Collectors.toList());
        }
        return contacts;
    }

}
