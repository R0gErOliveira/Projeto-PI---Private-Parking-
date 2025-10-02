/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.privateParking.repository;

import com.privateParking.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ROGER
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar um usuário pelo seu nome de usuário (username)
    Optional<Usuario> findByUsername(String username);
}
