/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.service;
import com.privateParking.model.RegistroEstacionamento;
import com.privateParking.repository.RegistroEstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistroEstacionamentoService {
    @Autowired // 2. Injeção de dependência: O Spring vai nos dar uma instância do Repository pronta para usar
    private RegistroEstacionamentoRepository repository;

    // 3. Vamos criar um método para salvar um novo registro
    public RegistroEstacionamento salvar(RegistroEstacionamento registro) {
        // Aqui poderíamos adicionar regras de negócio antes de salvar
        return repository.save(registro);
    }

   
}