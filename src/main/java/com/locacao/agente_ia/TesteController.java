package com.locacao.agente_ia;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat") // Agora a URL base será /chat
public class TesteController {

    private final AssistenteLocacao assistente;

    // Injeção de dependência: O Spring traz o assistente pronto pra nós
    public TesteController(AssistenteLocacao assistente) {
        this.assistente = assistente;
    }

    @PostMapping
    public String falarComIA(@RequestBody String mensagem) {
        // Estamos fixando o usuário 1 para teste.
        // Em produção, isso viria do login (Spring Security).
        int userId = 1;
        return assistente.conversar(userId, mensagem);
    }
}