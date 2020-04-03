package com.unit.test.pwc.address.book.rest.controller;


import com.pwc.address.book.AddressBookApplication;
import com.pwc.address.book.entities.Contact;
import com.pwc.address.book.service.AddressBookService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Objects;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class AddressBookIntegrationTest {

    private static final String TEST_ADDRESS_BOOK = "address-bookA";
    private static final String CONTACTS_END_POINT = "/api/v1/address-book/address-bookA/contacts";


    @LocalServerPort
    private int port;

    @Autowired
    private AddressBookService addressBookService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testAddContact_phoneNumber_Validation_failure() {
        Contact contact = new Contact("Rashmi", "Bhat", Arrays.asList("a312123345", "0312123346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<Contact> response = restTemplate.exchange(
                createURLWithPort(CONTACTS_END_POINT),
                HttpMethod.POST, entity, Contact.class);
        Assert.assertEquals(400, response.getStatusCode().value());
     }
    @Test
    public void testAddContact_success() {
        Contact contact = new Contact("Rashmi", "Bhat", Arrays.asList("0312123345", "0312123346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<Contact> response = restTemplate.exchange(
                createURLWithPort(CONTACTS_END_POINT),
                HttpMethod.POST, entity, Contact.class);

        Assert.assertEquals(200, response.getStatusCode().value());

        addressBookService.removeContact(TEST_ADDRESS_BOOK, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testRemoveContact_notfound_failure() {
       String deletionUrl = createURLWithPort("/api/v1/address-book/" + TEST_ADDRESS_BOOK + "/contacts/" + "04ff0485-7eef-4a91-b85b-d58bd4d4e219");
        ResponseEntity<String> response=restTemplate.exchange(
                deletionUrl,
                HttpMethod.DELETE, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(500, response.getStatusCode().value());

    }

    @Test
    public void testRemoveContact_success() {
        Contact contact = new Contact("first-name", "last-name", Arrays.asList("0412123345", "0412123346"));
        Contact addedContact = addressBookService.addContact(TEST_ADDRESS_BOOK, contact);

        String deletionUrl = createURLWithPort("/api/v1/address-book/" + TEST_ADDRESS_BOOK + "/contacts/" + addedContact.getId());

        ResponseEntity<String> response = restTemplate.exchange(
                deletionUrl,
                HttpMethod.DELETE, new HttpEntity<String>(null, null), String.class);

        Assert.assertEquals(200, response.getStatusCode().value());

        Assert.assertFalse(addressBookService.retrieveContacts(TEST_ADDRESS_BOOK).stream()
                .anyMatch(contact1 -> contact1.getId().equals(addedContact.getId())));
    }

    @Test
    public void testRetrieve_All_ContactsFromAddressBook_success() {
        Contact contact = new Contact("first-name", "last-name", Arrays.asList("0412123345", "0412123346"));
        addressBookService.addContact(TEST_ADDRESS_BOOK, contact);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/address-book/address-bookA/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void testRetrieve_UniqueContacts_success() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void testRetrieve_Common_Contacts_success() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/contacts?condition=COMMON"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
     }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
