package com.locacao.agente_ia;

import dev.langchain4j.service.spring.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class TesteController {

    private final AssistenteLocacao assistente;

    // Simulando banco de dados
    private static final Map<Integer, Usuario> USUARIOS = Map.of(
            1, new Usuario("Wedson Gomes", "JUNIOR_DEV", 2000.0),
            2, new Usuario("Elon Musk", "DIRETOR", 100000.0)
    );

    // Gambiarra didática para a Tool acessar o usuário (em prod usamos Spring Security)
    private static Usuario usuarioAtual;

    public static Usuario getUsuarioLogado() {
        return usuarioAtual;
    }

    public TesteController(AssistenteLocacao assistente) {
        this.assistente = assistente;
    }

    @PostMapping
    public String falarComIA(
            @RequestBody String mensagem,
            @RequestHeader(value = "user-id", defaultValue = "1") int userId
    ) {
        // 1. Simula o login
        usuarioAtual = USUARIOS.get(userId);
        if (usuarioAtual == null) return "Usuário não encontrado.";

        System.out.println("👤 FALANDO COM: " + usuarioAtual.nome());

        // 2. Chama a IA passando o contexto do usuário
        return assistente.conversar(
                userId,           // ID para a Memória (histórico)
                mensagem,         // Mensagem do User
                usuarioAtual.nome(), // Variável {{nome}}
                usuarioAtual.cargo() // Variável {{cargo}}
        );
    }
}