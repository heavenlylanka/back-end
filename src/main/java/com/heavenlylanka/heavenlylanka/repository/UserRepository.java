package com.heavenlylanka.heavenlylanka.repository;
import com.heavenlylanka.heavenlylanka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}