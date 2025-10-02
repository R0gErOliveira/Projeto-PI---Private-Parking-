package com.privateParking.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.repository.RegistroEstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaturamentoService {

    @Autowired
    private RegistroEstacionamentoRepository registroRepository;

    public List<RegistroEstacionamento> calcularFaturamentoMensal(int ano, int mes) {
        // lógica simples: apenas chamar o método do repositório.
        
        return registroRepository.findByAnoAndMesSaida(ano, mes);
    }
}