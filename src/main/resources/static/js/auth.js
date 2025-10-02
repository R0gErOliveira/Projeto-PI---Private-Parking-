// Espera o documento HTML ser completamente carregado antes de executar o script
document.addEventListener('DOMContentLoaded', function () {

    // Captura o formulário de redefinição de senha
    const formRedefinirSenha = document.getElementById('formRedefinirSenha');

    if (formRedefinirSenha) {
        formRedefinirSenha.addEventListener('submit', function (event) {
            // Previne o comportamento padrão do formulário (que seria recarregar a página)
            event.preventDefault();

            // Pega os valores dos campos do modal
            const username = document.getElementById('nomeUsuario').value;
            const newPassword = document.getElementById('novaSenha').value;

            // =======================================================
            // LINHAS REMOVIDAS DAQUI
            // const csrfToken = document.querySelector('input[name="_csrf"]').value;
            // const csrfHeader = document.querySelector('input[name="_csrf"]').getAttribute('name');
            // =======================================================

            // Cria um objeto FormData para enviar os dados
            const formData = new FormData();
            formData.append('username', username);
            formData.append('newPassword', newPassword);

            // Faz a requisição para o nosso endpoint no backend
            fetch('/redefinir-senha', {
                method: 'POST',
                
                body: new URLSearchParams(formData) // Envia os dados no formato que o @RequestParam espera
            })
            .then(response => {
                // O 'response.ok' será true se o status for 2xx (ex: 200 OK)
                if (response.ok) {
                    return response.text(); // Pega a mensagem de sucesso do backend
                } else {
                    // Se o status for 4xx ou 5xx, lança um erro com a mensagem do backend
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(successMessage => {
                // Se a requisição foi bem-sucedida
                alert(successMessage); // Exibe "Senha redefinida com sucesso!"
                
                // Fecha o modal (usando o Bootstrap JS)
                const modal = bootstrap.Modal.getInstance(document.getElementById('modalEsqueciSenha'));
                modal.hide();
                
                formRedefinirSenha.reset(); // Limpa o formulário
            })
            .catch(error => {
                // Se a requisição falhou (ex: usuário não encontrado)
                alert('Erro: ' + error.message); // Exibe "Erro: Usuário 'xxx' não encontrado."
            });
        });
    }
});