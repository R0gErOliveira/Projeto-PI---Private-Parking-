/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.service;

import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.repository.RegistroEstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PedidoService {

    @Autowired
    private RegistroEstacionamentoRepository registroRepository;

    /**
     * Finaliza o estacionamento de um veículo. Calcula o valor total, atualiza
     * a data de saída e salva no banco.
     *
     * @param placa A placa do veículo.
     * @param dataSaida A data em que o veículo está saindo.
     * @return O registro do estacionamento atualizado.
     */
    @Transactional // Garante que todas as operações com o banco sejam uma única transação
    public RegistroEstacionamento finalizarPedido(String placa, LocalDate dataSaida, BigDecimal valorDiaria) {

        // 1. Busca o registro ativo no banco pela placa
        RegistroEstacionamento registro = registroRepository.findByPlacaAndDataSaidaIsNull(placa)
                .orElseThrow(() -> new RuntimeException("Veículo com a placa '" + placa + "' não encontrado ou já finalizado."));

        // 2. Validação da data de saída
        if (dataSaida.isBefore(registro.getDataEntrada())) {
            throw new RuntimeException("A data de saída não pode ser anterior à data de entrada.");
        }

        // 3. Cálculo do número de dias
        // ChronoUnit.DAYS.between calcula o número de dias completos entre duas datas.
        // Somamos 1 para garantir que mesmo uma estadia de menos de 24h conte como 1 diária.
        long numeroDeDias = ChronoUnit.DAYS.between(registro.getDataEntrada(), dataSaida) + 1;

        // 4. Cálculo do valor total
        BigDecimal valorTotal = valorDiaria.multiply(new BigDecimal(numeroDeDias));

        // 5. Atualiza o objeto com os novos dados
        registro.setDataSaida(dataSaida);
        registro.setValorTotal(valorTotal);

        // 6. Salva o registro atualizado no banco de dados
        // Como o método é @Transactional, o JPA/Hibernate salva as alterações automaticamente
        // quando a transação é concluída (commit). A chamada save() é uma boa prática para clareza.
        return registroRepository.save(registro);
    }
}
