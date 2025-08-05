package com.games.aiagengt.config;

import com.games.aiagengt.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class QwenChatMemory {

    // 条数
    @Value("${qwen.chat.memory.max-messages}")
    private int withMaxMessagesConfig;


    // 持久存储
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;
    /*
     *
     * 配置ChatMemory
     *
     * */

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(withMaxMessagesConfig);
    }

    // 本地内存向量数据库
    /*@Bean
    ContentRetriever contentRetriever() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\src\\main\\resources\\md\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\src\\main\\resources\\md\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\src\\main\\resources\\md\\神经科室.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);
        //使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        //从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }*/

    @Autowired
    private EmbeddingStore embeddingStore;
    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    /**
     * Pinecone的向量数据库配
     * @Description: []
     * @Param: []
     * @return: dev.langchain4j.rag.content.retriever.ContentRetriever
     * @Author: games
     * @Date: 下午5:39 2025/6/20
     */
    ContentRetriever contentRetrieverXiaozhiPincone() {
        // 创建一个 EmbeddingStoreContentRetriever 对象，用于从嵌入存储中检索内容
        return EmbeddingStoreContentRetriever
                .builder()
                // 设置用于生成嵌入向量的嵌入模型
                .embeddingModel(embeddingModel)
                // 指定要使用的嵌入存储
                .embeddingStore(embeddingStore)
                // 设置最大检索结果数量，这里表示最多返回 1 条匹配结果
                .maxResults(1)
                // 设置最小得分阈值，只有得分大于等于 0.8 的结果才会被返回
                .minScore(0.8)
                //构建最终的 EmbeddingStoreContentRetriever 实例
                .build();
    }

    /*
     *
     * 配置ChatMemoryProvider
     * https://docs.langchain4j.dev/tutorials/ai-services#chat-memory
     * */
    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(withMaxMessagesConfig)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

}
