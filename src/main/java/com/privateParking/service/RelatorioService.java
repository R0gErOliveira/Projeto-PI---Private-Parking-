/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.service;

import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.repository.RegistroEstacionamentoRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RegistroEstacionamentoRepository registroRepository;

    /**
     * Busca os registros de estacionamento no banco de dados com base em um período de datas.
     * @param dataInicial A data de início do filtro.
     * @param dataFinal A data de fim do filtro.
     * @return Uma lista de registros encontrados.
     */
    public List<RegistroEstacionamento> buscarRegistrosPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        // lógica para garantir que a data final inclua o dia inteiro,
      
        
        return registroRepository.findByDataEntradaBetween(dataInicial, dataFinal);
    }
    /**
     * Exclui um registro de estacionamento com base no seu ID.
     * @param id O ID do registro a ser excluído.
     */
    public void excluirRegistroPorId(Long id) {
        // Verifica se o registro existe antes de tentar deletar,
        // para evitar exceções se o ID for inválido.
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado com o ID: " + id);
        }
        registroRepository.deleteById(id);
    }
}