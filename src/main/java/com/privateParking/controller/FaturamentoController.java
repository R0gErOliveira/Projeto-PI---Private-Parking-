/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;
import com.privateParking.service.FaturamentoService;
import com.privateParking.model.RegistroEstacionamento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping 
public class FaturamentoController {

      @Autowired
    private FaturamentoService faturamentoService;

    // Método que exibe a página HTML
    @GetMapping("/faturamento")
    public String exibirPaginaFaturamento() {
        return "faturamento";
    }

    // ============================ MÉTODO (ENDPOINT DA API) ============================
    @GetMapping("/api/faturamento")
    @ResponseBody // Garante que a resposta será JSON, não uma página
    public ResponseEntity<List<RegistroEstacionamento>> buscarFaturamento(
            @RequestParam("ano") int ano,
            @RequestParam("mes") int mes) {
        
        List<RegistroEstacionamento> registros = faturamentoService.calcularFaturamentoMensal(ano, mes);
        
        return ResponseEntity.ok(registros);
    }
    // =====================================================================================
}