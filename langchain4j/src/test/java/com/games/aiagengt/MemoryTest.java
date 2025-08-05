package com.games.aiagengt;

import com.games.aiagengt.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class MemoryTest {

    @Autowired
    private QwenChatModel qwenChatModel;
    @Autowired
    private Assistant assistant;

    /*
    * 你好，jjb！很高兴认识你。有什么我可以帮助你的吗？如果你有任何问题或需要讨论某些话题，随时告诉我哦。
    你刚才提到你叫jjb，所以在这里我理解jjb就是你。如果你是指其他人或有其他特定的含义，请告诉我更多信息，这样我才能更好地帮助你。
    * */
    @Test
    public void testAssistant() {
        UserMessage userMessage = UserMessage.userMessage("我叫 jjb");
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        System.out.println(aiMessage.text());

        UserMessage userMessage1 = UserMessage.userMessage("谁是jjb");
        ChatResponse chatResponse1 = qwenChatModel.chat(Arrays.asList(userMessage, aiMessage, userMessage1));//数组
        System.out.println(chatResponse1.aiMessage().text());
    }


    MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.withMaxMessages(10);

    /*
    * 记忆 MessageWindowChatMemory
    * */

    @Test
    public void testMemory() {
        Assistant assistant = AiServices.builder(Assistant.class).chatLanguageModel(qwenChatModel).chatMemory(messageWindowChatMemory).build();
        String response = assistant.ask("我叫 jjb");
        System.out.println(response);
        String response1 = assistant.ask("谁是jjb");
        System.out.println(response1);
    }


}
