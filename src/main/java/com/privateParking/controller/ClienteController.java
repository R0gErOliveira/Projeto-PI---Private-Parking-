/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // O método GET 
    @GetMapping("/cadastroCliente")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("cliente", new RegistroEstacionamento());
        return "cadastroCliente";
    }

    // ================== MÉTODO POST ==================
    @PostMapping("/cadastroCliente")
    public String processarCadastroCliente(
            @Valid @ModelAttribute("cliente") RegistroEstacionamento cliente,
            BindingResult bindingResult) {

        // 1. Verifica se o BindingResult encontrou algum erro de validação
        if (bindingResult.hasErrors()) {
            // Se houver erros, não salva no banco e retorna para a página do formulário.
            // O Thymeleaf usará o objeto 'cliente' (com os dados já preenchidos)
            // e o 'bindingResult' (com os erros) para exibir as mensagens.
            return "cadastroCliente"; 
        }

        // 2. Se não houver erros, salva o cliente e redireciona
        clienteService.salvarCliente(cliente);
        return "redirect:/menu?cadastro=sucesso"; // Adicionamos um parâmetro para mostrar uma mensagem de sucesso no menu
    }
    // ==========================================================
}