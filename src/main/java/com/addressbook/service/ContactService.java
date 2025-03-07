package com.addressbook.service;

import com.addressbook.dto.ContactDTO;
import com.addressbook.model.Contact;
import com.addressbook.repo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    // Convert Contact to ContactDTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(contact.getName());
        contactDTO.setPhoneNumber(contact.getPhoneNumber());
        contactDTO.setEmail(contact.getEmail());
        return contactDTO;
    }

    // Convert ContactDTO to Contact
    private Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmail(contactDTO.getEmail());
        return contact;
    }

    // Get all contacts
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a contact by ID
    public ContactDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        return contact != null ? convertToDTO(contact) : null;
    }

    // Create a new contact
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return convertToDTO(savedContact);
    }

    // Update an existing contact
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            contact.setName(contactDTO.getName());
            contact.setPhoneNumber(contactDTO.getPhoneNumber());
            contact.setEmail(contactDTO.getEmail());
            Contact updatedContact = contactRepository.save(contact);
            return convertToDTO(updatedContact);
        }
        return null;
    }

    // Delete a contact
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}