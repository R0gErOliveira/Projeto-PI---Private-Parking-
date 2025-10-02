/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.privateParking.repository;

import com.privateParking.model.RegistroEstacionamento; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<RegistroEstacionamento, Long> {
    // O Spring Data JPA criará automaticamente os métodos save, findById, findAll, etc.
}