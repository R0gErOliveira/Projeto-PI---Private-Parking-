document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("filtroRelatorioForm");
    const tabelaBody = document.querySelector("#tabelaRelatorio tbody");

    
    const parseDateAsLocal = (dateString) => {
        const [year, month, day] = dateString.split('-').map(Number);
        return new Date(year, month - 1, day);
    };

    if (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const dataInicial = document.getElementById("dataInicial").value;
            const dataFinal = document.getElementById("dataFinal").value;

            // Validação simples para garantir que as datas foram preenchidas
            if (!dataInicial || !dataFinal) {
                alert("Por favor, selecione a data inicial e a data final.");
                return;
            }

            // ================== MUDANÇA PRINCIPAL AQUI ==================
            // Em vez de dados simulados, vamos buscar no backend.
            // A URL inclui as datas como parâmetros de consulta.
            const url = `/api/relatorio?dataInicial=${dataInicial}&dataFinal=${dataFinal}`;

            fetch(url)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Erro ao buscar os dados do relatório.');
                        }
                        return response.json(); // Converte a resposta do backend para JSON
                    })
                    .then(dadosDoBackend => {
                        // Agora usamos os dados recebidos para popular a tabela.
                        popularTabela(dadosDoBackend);
                    })
                    .catch(error => {
                        console.error('Erro na requisição:', error);
                        tabelaBody.innerHTML = `<tr><td colspan="9" class="text-center text-danger">Falha ao carregar o relatório.</td></tr>`;
                    });
            // ==========================================================
        });
    }

    // Função separada para popular a tabela
    function popularTabela(dados) {
        // Limpa a tabela
        tabelaBody.innerHTML = "";

        // Verifica se há dados
        if (dados.length === 0) {
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="9" class="text-center">Nenhum registro encontrado para o período selecionado.</td>`;
            tabelaBody.appendChild(row);
            return;
        }

        // Insere os dados recebidos
        dados.forEach(dado => {
            const row = document.createElement("tr");

            // Formata as datas recebidas do backend
            const dataEntradaFormatada = new Date(dado.dataEntrada).toLocaleDateString('pt-BR', {timeZone: 'UTC'});

            // O campo 'valor' virá do backend, e a data de saída pode ser nula
            const dataSaidaFormatada = dado.dataSaida ? new Date(dado.dataSaida).toLocaleDateString('pt-BR', {timeZone: 'UTC'}) : 'N/A';
            const valorFormatado = dado.valorTotal ? `R$ ${dado.valorTotal.toFixed(2).replace('.', ',')}` : 'N/A';

            row.innerHTML = `
                <td>${dado.nomeCliente}</td>
                <td>${dado.cpf}</td>
                <td>${dado.email}</td>
                <td>${dado.veiculo}</td>
                <td>${dado.placa}</td>
                <td>${dataEntradaFormatada}</td>
                <td>${dataSaidaFormatada}</td>
                <td>${valorFormatado}</td>
            
             <td class="text-center">
                    <button class="btn btn-danger btn-sm" onclick="excluirRegistro(${dado.id})">
                        Excluir
                    </button>
                </td>
            `;
            tabelaBody.appendChild(row);
        });
    }
    
     // Anexa a função à 'window' para que o 'onclick' do HTML possa encontrá-la.
    window.excluirRegistro = function(id) {
        // Pede confirmação ao usuário
        if (!confirm(`Tem certeza que deseja excluir este registro?`)) {
            return;
        }

        const url = `/api/relatorio/excluir/${id}`;

        fetch(url, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert("Registro excluído com sucesso!");
                // Recarrega a tabela para refletir a mudança.
                // Dispara o evento 'submit' do formulário para refazer a busca atual.
                form.dispatchEvent(new Event('submit'));
            } else {
                return response.text().then(text => { throw new Error(text || 'Falha ao excluir o registro.') });
            }
        })
        .catch(error => {
            console.error('Erro ao excluir:', error);
            alert(`Erro: ${error.message}`);
        });
    }
});