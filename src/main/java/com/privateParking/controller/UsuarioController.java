/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import com.privateParking.service.UsuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Este endpoint responderá a requisições POST para a URL /redefinir-senha
    // @RequestParam pega os dados enviados pelo formulário do modal
    @PostMapping("/redefinir-senha")
     @ResponseBody
    public ResponseEntity<String> redefinirSenha(@RequestParam("username") String username,
                                                 @RequestParam("newPassword") String newPassword) {
        try {
            usuarioService.redefinirSenha(username, newPassword);
            // ResponseEntity.ok() retorna um status 200 OK com uma mensagem de sucesso
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (Exception e) {
            // ResponseEntity.badRequest() retorna um status 400 Bad Request com a mensagem de erro
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}