document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("filtroFaturamentoForm");
    const tabelaBody = document.getElementById("tabelaFaturamentoBody");
    const faturamentoTotalEl = document.getElementById("faturamentoTotal");

    if (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const mesAno = document.getElementById("mesAno").value; // Formato: "YYYY-MM"
            if (!mesAno) {
                alert("Por favor, selecione um mês e ano.");
                return;
            }

            // Extrai o ano e o mês da string "YYYY-MM"
            const [ano, mes] = mesAno.split('-');

            // Monta a URL da API com os parâmetros de ano e mês
            const url = `/api/faturamento?ano=${ano}&mes=${mes}`;

            // Exibe um feedback de carregamento na tabela (opcional, mas bom para UX)
            tabelaBody.innerHTML = `<tr><td colspan="4" class="text-center">Buscando dados...</td></tr>`;
            faturamentoTotalEl.textContent = "Calculando...";

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro ao buscar os dados de faturamento.');
                    }
                    return response.json();
                })
                .then(dados => {
                    // Limpa a tabela antes de preencher
                    tabelaBody.innerHTML = "";

                    if (dados.length === 0) {
                        tabelaBody.innerHTML = `<tr><td colspan="4" class="text-center">Nenhum registro encontrado para o período selecionado.</td></tr>`;
                        faturamentoTotalEl.textContent = "R$ 0,00";
                        return;
                    }

                    let faturamentoTotal = 0;

                    // Preenche a tabela com os dados recebidos
                    dados.forEach(registro => {
                        const row = document.createElement("tr");
                        const valorPago = registro.valorTotal || 0; // Garante que não seja nulo
                        faturamentoTotal += valorPago;

                        row.innerHTML = `
                            <td>${registro.nomeCliente}</td>
                            <td>${registro.veiculo}</td>
                            <td>${registro.placa}</td>
                            <td class="text-end">${formatarMoeda(valorPago)}</td>
                        `;
                        tabelaBody.appendChild(row);
                    });

                    // Atualiza o card com o faturamento total
                    faturamentoTotalEl.textContent = formatarMoeda(faturamentoTotal);
                })
                .catch(error => {
                    console.error('Erro na requisição:', error);
                    tabelaBody.innerHTML = `<tr><td colspan="4" class="text-center text-danger">Falha ao carregar o faturamento.</td></tr>`;
                    faturamentoTotalEl.textContent = "Erro!";
                });
        });
    }

    // Função auxiliar para formatar valores como moeda brasileira
    function formatarMoeda(valor) {
        return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    }
});

