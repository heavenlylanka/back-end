package com.heavenlylanka.heavenlylanka.repository;

import com.heavenlylanka.heavenlylanka.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

}