/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.repository.RegistroEstacionamentoRepository;
import com.privateParking.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
// =======================================================

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    // ================== DEPENDÊNCIAS ==================
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private RegistroEstacionamentoRepository registroRepository;
    // =================================================

    
    @GetMapping
    public String exibirFormularioPedido() {
        return "pedido";
    }

    
    /**
     * Processa os dados do formulário de finalização de pedido.
     */
    @PostMapping("/finalizar")
    public String finalizarPedido(
            @RequestParam("placa") String placa,
            @RequestParam("dataSaida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSaida,
            @RequestParam("valorDiaria") BigDecimal valorDiaria,
            RedirectAttributes redirectAttributes) { 
        
        try {
            RegistroEstacionamento registroFinalizado = pedidoService.finalizarPedido(placa, dataSaida, valorDiaria);
            // Em caso de sucesso, redireciona para a página de confirmação
            return "redirect:/pedido/confirmacao/" + registroFinalizado.getId();
        } catch (RuntimeException e) {
            // Em caso de erro, adiciona a mensagem de erro e redireciona de volta ao formulário
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/pedido";
        }
    }

    /**
     * Exibe a página de confirmação com os detalhes do pedido finalizado.
     */
    @GetMapping("/confirmacao/{id}")
    public String exibirConfirmacao(@PathVariable("id") Long id, Model model) {
        RegistroEstacionamento registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado!"));
        
        long dias = ChronoUnit.DAYS.between(registro.getDataEntrada(), registro.getDataSaida()) + 1;
        
        model.addAttribute("registro", registro);
        model.addAttribute("diasEstacionado", dias);
        
        return "confirmacao-pedido";
    }
}