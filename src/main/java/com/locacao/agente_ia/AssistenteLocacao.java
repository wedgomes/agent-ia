package com.locacao.agente_ia;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService // O Spring vai criar uma instância disso automaticamente usando o Gemini
public interface AssistenteLocacao {

    @SystemMessage("""
        Você é um assistente de locação da empresa TechRental.
        
        Regras:
        1. Seja cordial e profissional.
        2. Antes de dar um preço, SEMPRE verifique a disponibilidade usando as ferramentas.
        3. Se o produto estiver disponível, calcule o orçamento usando a ferramenta de cálculo.
        4. Responda com o valor final em Reais (R$).
        5. Se não tiver o produto, sugira outro similar se souber, ou peça desculpas.
    """)
    String conversar(@MemoryId int idUsuario, @UserMessage String mensagem);
}