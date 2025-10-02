/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.config;

/**
 *
 * @author ROGER
 */
import com.privateParking.model.Role;
import com.privateParking.model.Usuario;
import com.privateParking.repository.RoleRepository;
import com.privateParking.repository.UsuarioRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cria os papéis caso não existam
        Role roleAtendente = roleRepository.findByName("ROLE_ATENDENTE");
        if (roleAtendente == null) {
            roleAtendente = new Role("ROLE_ATENDENTE");
            roleRepository.save(roleAtendente);
        }

        Role roleGerente = roleRepository.findByName("ROLE_GERENTE");
        if (roleGerente == null) {
            roleGerente = new Role("ROLE_GERENTE");
            roleRepository.save(roleGerente);
        }

        // Cria o usuário 'atendente' se não existir
        if (usuarioRepository.findByUsername("atendente").isEmpty()) {
            Usuario atendente = new Usuario();
            atendente.setUsername("atendente");
            atendente.setPassword(passwordEncoder.encode("123")); // senha criptografada
            atendente.setRoles(Set.of(roleAtendente));
            usuarioRepository.save(atendente);
        }

        // Cria o usuário 'gerente' se não existir
        if (usuarioRepository.findByUsername("gerente").isEmpty()) {
            Usuario gerente = new Usuario();
            gerente.setUsername("gerente");
            gerente.setPassword(passwordEncoder.encode("admin")); // senha criptografada
            gerente.setRoles(Set.of(roleAtendente, roleGerente)); // gerente tem ambos os papéis
            usuarioRepository.save(gerente);
        }
    }
}