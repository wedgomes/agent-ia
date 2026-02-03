package com.locacao.agente_ia;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService // O Spring vai criar uma instância disso automaticamente usando o Gemini
public interface AssistenteLocacao {

    @SystemMessage("""
        Você é um assistente da TechRental.
        Você está falando com {{nome}}, que ocupa o cargo de {{cargo}}.
        
        Regras:
        1. Seja cordial. Se for um DIRETOR, seja mais formal.
        2. Use as ferramentas para verificar estoque e calcular preço.
        3. Se o usuário quiser fechar negócio, USE A FERRAMENTA 'finalizarPedido'.
        4. Se a ferramenta retornar BLOQUEADO, explique o motivo educadamente.
    """)
    String conversar(@MemoryId int idUsuario, @UserMessage String mensagem, @V("nome") String nome, @V("cargo") String cargo);
}