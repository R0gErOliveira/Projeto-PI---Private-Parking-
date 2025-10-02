package com.privateParking.service;

import com.privateParking.model.Role;
import com.privateParking.model.Usuario;
import com.privateParking.repository.UsuarioRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// =================================================================

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no nosso banco de dados pelo username
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o username: " + username));

        // Converte nossos Roles para o formato que o Spring Security entende (GrantedAuthority)
        Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(usuario.getRoles());

        // Retorna um objeto User que o Spring Security usa para autenticação
        // Agora, o Java sabe que este "new User" é da classe correta do Spring Security
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}