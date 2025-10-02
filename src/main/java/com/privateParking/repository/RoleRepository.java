/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.privateParking.repository;

import com.privateParking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ROGER
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
