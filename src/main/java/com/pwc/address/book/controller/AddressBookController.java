package com.pwc.address.book.controller;

import com.pwc.address.book.entities.Contact;
import com.pwc.address.book.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author  rashmi
 */
@RestController
@Api(value="/api/v1/address-book",description="Address Book controller by Rashmi.",produces ="application/json")

public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * API to add teh contacts in to a specified address book
     * @param addressBookId
     * @param nContact
     * @return
     */

    @ApiOperation(value = "Adds contacts to the Address book.",
            notes = "The successful invocation of this request will give you the Contact informations thats stored")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully storing the contact")

    })
    @PostMapping("/api/v1/address-book/{addressBookId}/contacts")
    public Contact addContactToAddressBook(
            @PathVariable String addressBookId, @Valid @RequestBody Contact nContact) {

        return addressBookService.addContact(addressBookId, nContact);
    }


    /**
     * API to remove the contact  from a specified address book
     * @param addressBookId
     * @return
     */

    @ApiOperation(value = "Removes the given contact from the Address book.",
            notes = "The successful invocation of this request will remove the contact details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully storing the contact")

    })
    @DeleteMapping("/api/v1/address-book/{addressBookId}/contacts/{contactId}")
    public Contact removeContactFromAddressBook(@PathVariable String addressBookId,
                                                @PathVariable String contactId) {
        return addressBookService.removeContact(addressBookId, contactId);
    }

    /**
     * API to Get All the contacts  from a specified address book
     * @param addressBookId
     * @return
     */

    @ApiOperation(value = "Get All the contacts  from a specified address book.",
            notes = "On successful invocation All the contacts from a specified address book will be retrieved.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully storing the contact")

    })

    @GetMapping("/api/v1/address-book/{addressBookId}/contacts")
    public List<Contact> retrieveContactsFromAddressBook(@PathVariable String addressBookId) {
        return addressBookService.retrieveContacts(addressBookId);
    }

    /**
     * API to Get All the contacts  from a specified address book
     * @return
     */

    @ApiOperation(value = "retrieves All Unique Contacts",
            notes = "On successful invocation All the contacts from a specified address book will be retrieved.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully storing the contact")

    })
    @GetMapping("/api/v1/contacts")
    public List<Contact> retrieveUniqueContactsFromAllAddressBooks(@RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        return addressBookService.retrieveAllUniqueContacts(unique);
    }

}