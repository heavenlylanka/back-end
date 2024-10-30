package com.heavenlylanka.heavenlylanka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heavenlylanka.heavenlylanka.repository.ContactMessageRepository;
import com.heavenlylanka.heavenlylanka.entity.ContactMessage;

import java.util.List;
@Service
public class AdminContactMessageService {
    private final ContactMessageRepository repository;

    @Autowired
    public AdminContactMessageService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }

    public ContactMessage getMessageById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteMessage(Long id) {
        repository.deleteById(id);
    }
}
