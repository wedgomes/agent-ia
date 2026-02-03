package com.locacao.agente_ia;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Diz ao Spring: "Isso aqui configura o sistema"
public class GeminiConfig {

    // Pega a chave que você colocou no application.properties
    @Value("${langchain4j.google-ai-gemini.chat-model.api-key}")
    private String apiKey;

    @Bean // Cria o objeto "Cérebro" e deixa disponível para o @AiService usar
    public ChatLanguageModel model() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.0) // Precisão máxima
                .logRequestsAndResponses(true) // Para vermos o log no console
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        // Para cada usuário/conversa, cria uma memória que segura as últimas 10 mensagens
        return memoryId -> MessageWindowChatMemory.withMaxMessages(10);
    }
}