package com.heavenlylanka.heavenlylanka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heavenlylanka.heavenlylanka.repository.ContactMessageRepository;
import com.heavenlylanka.heavenlylanka.entity.ContactMessage;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final ContactMessageRepository contactMessageRepository;

    @Autowired
    public AdminController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/contact-messages")
    public List<ContactMessage> getContactMessages() {
        return contactMessageRepository.findAll();
    }
}
