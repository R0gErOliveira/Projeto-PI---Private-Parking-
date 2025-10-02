/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Este método será chamado quando alguém acessar a URL principal do site.
    // O @GetMapping("/") significa "a raiz do site".
    @GetMapping("/")
    public String index() {
        // Retorna o nome do arquivo HTML que deve ser exibido.
        // O Spring/Thymeleaf vai procurar por "index.html" em "src/main/resources/templates/".
        return "index"; 
    }
    
    @GetMapping("/menu")
public String exibirMenu() {
    // Este método retorna a página "menu.html" quando a URL /menu for acessada
    return "menu";
}

@GetMapping("/relatorio")
public String exibirPaginaRelatorio() {
    // Retorna o nome do template "relatorio.html"
    return "relatorio";
}

@GetMapping("/acesso-negado")
public String acessoNegado() {
    return "acesso-negado";
}

}