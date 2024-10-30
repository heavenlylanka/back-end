package com.heavenlylanka.heavenlylanka.controller;

import com.heavenlylanka.heavenlylanka.entity.ContactMessage;
import com.heavenlylanka.heavenlylanka.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contact")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @Autowired
    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<String> submitMessage(@RequestBody ContactMessage message) {
        contactMessageService.saveMessage(message);
        return new ResponseEntity<>("Message received successfully!", HttpStatus.OK);
    }
}