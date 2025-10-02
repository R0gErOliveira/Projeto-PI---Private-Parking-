/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.privateParking.repository;
import com.privateParking.model.RegistroEstacionamento;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository 
public interface RegistroEstacionamentoRepository extends JpaRepository<RegistroEstacionamento, Long> {
   
    
    /**
     * Encontra todos os registros de estacionamento cuja data de entrada
     * está entre a data inicial e a data final fornecidas (inclusivo).
     * O Spring Data JPA gera a consulta SQL automaticamente a partir do nome do método.
     */
    List<RegistroEstacionamento> findByDataEntradaBetween(LocalDate dataInicial, LocalDate dataFinal);
    // =================================================
    
    
  
    /**
     * Encontra um registro de estacionamento ativo (que ainda não tem data de saída)
     * com base na placa do veículo.
     * Retorna um Optional porque o registro pode não existir.
     */
    Optional<RegistroEstacionamento> findByPlacaAndDataSaidaIsNull(String placa);
    // ===================================================================
    
    
    /**
     * Encontra todos os registros de estacionamento que foram finalizados (têm data de saída)
     * em um determinado mês e ano.
     * A consulta usa funções do banco de dados (YEAR e MONTH) para extrair o ano e o mês
     * da coluna data_saida.
     */
    @Query("SELECT r FROM RegistroEstacionamento r WHERE r.dataSaida IS NOT NULL AND YEAR(r.dataSaida) = :ano AND MONTH(r.dataSaida) = :mes")
    List<RegistroEstacionamento> findByAnoAndMesSaida(@Param("ano") int ano, @Param("mes") int mes);
    // ===================================================================
}