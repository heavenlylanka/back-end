package com.heavenlylanka.heavenlylanka.repository;
import com.heavenlylanka.heavenlylanka.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
