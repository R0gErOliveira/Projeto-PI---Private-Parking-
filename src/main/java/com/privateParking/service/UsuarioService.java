/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.service;

import com.privateParking.model.Usuario;
import com.privateParking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Transactional garante que a operação inteira seja concluída com sucesso ou revertida em caso de erro.
    @Transactional
    public void redefinirSenha(String username, String newPassword) {
        // 1. Encontra o usuário no banco de dados
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário '" + username + "' não encontrado."));

        // Validação básica da nova senha (pode ser melhorada)
        if (newPassword == null || newPassword.length() < 3) { // Exemplo: senha deve ter no mínimo 3 caracteres
            throw new IllegalArgumentException("A nova senha é muito curta.");
        }

        // 2. Criptografa a nova senha
        String senhaCriptografada = passwordEncoder.encode(newPassword);

        // 3. Atualiza a senha do usuário
        usuario.setPassword(senhaCriptografada);

        // 4. Salva o usuário com a nova senha no banco
        usuarioRepository.save(usuario);
    }
}