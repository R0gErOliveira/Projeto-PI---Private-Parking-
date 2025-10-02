/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.controller;

import com.privateParking.model.RegistroEstacionamento; 
import com.privateParking.service.RelatorioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api") // Define um prefixo "/api" para todas as rotas neste controller
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/relatorio")
    public ResponseEntity<List<RegistroEstacionamento>> gerarRelatorio(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<RegistroEstacionamento> registros = relatorioService.buscarRegistrosPorPeriodo(dataInicial, dataFinal);
        
        return ResponseEntity.ok(registros);
    }
    
     // ================== MÉTODO (ENDPOINT DE EXCLUSÃO) ==================
    @DeleteMapping("/relatorio/excluir/{id}")
    public ResponseEntity<?> excluirRegistro(@PathVariable Long id) {
        try {
            relatorioService.excluirRegistroPorId(id);
            // Retorna uma resposta vazia com status 200 OK para indicar sucesso.
            return ResponseEntity.ok().build(); 
        } catch (RuntimeException e) {
            // Se o serviço lançar a exceção de "não encontrado",
            // retorna a mensagem de erro com status 400 Bad Request.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}