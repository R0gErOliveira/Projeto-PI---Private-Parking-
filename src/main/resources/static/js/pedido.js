document.addEventListener("DOMContentLoaded", function () {
    const pedidoForm = document.getElementById("pedidoForm");

    if (pedidoForm) {
        pedidoForm.addEventListener("submit", function (e) {
            const placa = document.getElementById("placa").value.trim();
            const dataSaida = document.getElementById("dataSaida").value;
            const valorDiaria = document.getElementById("valorDiaria").value;

            if (!placa || !dataSaida || !valorDiaria) {
                alert("Por favor, preencha todos os campos.");
                e.preventDefault(); // Impede o envio APENAS se houver campos vazios.
            }
            // Se tudo estiver preenchido, o formulário será enviado ao backend.
        });
    }
});