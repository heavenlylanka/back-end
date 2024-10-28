package com.heavenlylanka.heavenlylanka.controller;

import com.heavenlylanka.heavenlylanka.service.AdminContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.heavenlylanka.heavenlylanka.entity.ContactMessage;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/contact-messages")
public class AdminContactMessageController {

    private final AdminContactMessageService contactMessageService;

    @Autowired
    public AdminContactMessageController(AdminContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return contactMessageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ContactMessage getMessageById(@PathVariable Long id) {
        return contactMessageService.getMessageById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        contactMessageService.deleteMessage(id);
        return "Message deleted successfully.";
    }
}
