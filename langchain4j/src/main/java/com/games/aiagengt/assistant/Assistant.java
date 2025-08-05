package com.games.aiagengt.assistant;
//因为我们在配置文件中同时配置了多个大语言模型，所以需要在这里明确指定（EXPLICIT）模型的beanName

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;


@AiService( // 指定使用显式装配模式，明确声明所使用的模型及相关组件
        wiringMode = EXPLICIT, // 使用配置文件中定义的依赖进行装配
        //chatModel = "qwenChatModel", // 使用名为"qwenChatModel"的Bean作为对话模型
        streamingChatModel = "qwenStreamingChatModel", // 使用名为"qwenChatModel"的Bean作为流失模型对话模型
//        chatMemory = "chatMemory", // 使用名为"chatMemory"的Bean管理对话记忆
        chatMemoryProvider = "chatMemoryProvider", // 使用名为"chatMemoryProvider"的Bean提供记忆存储服务
        //tools="calculatorTools"
        tools="appointmentTools",
        //contentRetriever = "contentRetriever" // 内存数据库
        contentRetriever = "contentRetrieverXiaozhiPincone" // pincone向量数据库
)

/*
*
* https://github.com/langchain4j/langchain4j-examples/blob/main/other-examples/src/main/java/ServiceWithPersistentMemoryForEachUserExample.java
* */
public interface Assistant {
    // 向助手提问
    String ask(String prompt);

    /*
    * 不同用户
    * */
    // 向指定用户提问
    String ask(@MemoryId int memoryId, @UserMessage String prompt);
    //String ask(@MemoryId Long memoryId, @UserMessage String prompt);

    // 使用@SystemMessage注解，从zhaozhi-prompt-template.txt文件中获取消息
    //    @SystemMessage(fromResource = "zhaozhi-prompt-template.txt")
    // 定义一个名为chat的方法，返回一个Flux类型的字符串流
    Flux<String> ask(@MemoryId Long memoryId, @UserMessage String userMessage);
    //    @SystemMessage("你是我的好朋友，请用猪猪侠话回答问题。今天是{{current_date}}")//系统消息提示词
    //    String askPrompt(@MemoryId int memoryId, @UserMessage String prompt);

    /* 文件识别有问题 */
    @SystemMessage(fromResource  = "/prompt-template.txt")
    String askFromResource(@MemoryId int memoryId, @UserMessage String userMessage);

    @UserMessage("你是我的好朋友，请用广西话回答问题，并且添加一些表情符号。 {{it}}") //{{it}}表示这里
    String chatUserMessage(String message);

    @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。{{message}}")
    String chatUserMessageV(@V("message") String userMessage);


    @UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
    String chatUserMessageV2(@MemoryId int memoryId, @V("message") String userMessage);

    @SystemMessage(fromResource = "prompt-template.txt")
    String chatSystemMessageV2(
            @MemoryId int memoryId,
            @UserMessage String userMessage,
            @V("username") String username,
            @V("age") int age
    );
}
