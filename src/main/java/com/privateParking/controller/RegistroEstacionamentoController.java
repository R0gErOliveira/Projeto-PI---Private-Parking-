/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registros") // 1. Todas as URLs tratadas por este controller começarão com /registros
public class RegistroEstacionamentoController {

    // 2. Método para mostrar a página do formulário de cadastro
    // Ele será acessado pela URL: http://localhost:8080/registros/novo
    @GetMapping("/novo")
    public String mostrarFormularioDeCadastro() {
        // 3. Retorna o nome do arquivo HTML que deve ser renderizado
        // O arquivo deve estar em: src/main/resources/templates/formulario-registro.html
        return "formulario-registro"; 
    }

   
}