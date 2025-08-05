package com.games.aiagengt;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName AppointmentServiceTest
 * @PackageName com.games.aiagengt
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/5/29 下午4:31
 * @Version 1.0
 */
@SpringBootTest
public class LoadDocumentTest {

    @Test
    /**
     * @Description: []
     * @Param: []
     * @return: void
     * @Author: games
     * @Date: 下午3:49 2025/6/16
     */ public void testReadDocument() {
//使用FileSystemDocumentLoader读取指定目录下的知识库文档
//并使用默认的文档解析器TextDocumentParser对文档进行解析C:\Res-20240514\SGG\gg
        Document document0 = FileSystemDocumentLoader.loadDocument("C:/Res-20240514/SGG/gg/abc.txt");
        System.out.println(document0.text());
        Document document1 = FileSystemDocumentLoader.loadDocument("C:/Res-20240514/SGG/gg/abc.txt", new TextDocumentParser());
        System.out.println(document1.text());

     /*
// 从一个目录中加载所有文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("C:/Res-20240514/SGG/gg", new
                TextDocumentParser());
// 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documents1 = FileSystemDocumentLoader.loadDocuments("C:/Res-20240514/SGG/gg",
                pathMatcher, new TextDocumentParser());
// 从一个目录及其子目录中加载所有文档
        List<Document> documents2 =
                FileSystemDocumentLoader.loadDocumentsRecursively("C:/Res-20240514/SGG/gg", new
                        TextDocumentParser());*/
    }

    /**
     * 解析PDF
     */
    @Test
    public void testParsePDF() {
        Document document = FileSystemDocumentLoader.loadDocument("C:/Res-20240514/SGG/gg/尚硅谷-Java+大模型应用-硅谷小智（医疗版）.pdf", new ApachePdfBoxDocumentParser());
        System.out.println(document);
    }


    /**
     * 加载文档并存入向量数据库
     */
    @Test
    public void testReadDocumentAndStore() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\HELP-PS.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //ingest
        //1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300个token，并且有 30 个token的重叠部分保证连贯性
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        //3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        //查看向量数据库内容
        System.out.println(embeddingStore);
    }

    @Test
    public void testDocumentSplitter2() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\HELP.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //自定义文档分割器
        //按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        //token分词器：按token计算
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30, new HuggingFaceTokenizer());
        //按字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);
    }

    @Test
    public void testDocumentSplitter() {
        // 1. 加载文档
        Document document = FileSystemDocumentLoader.loadDocument("C:\\Res-20240514\\ai-agent\\ai-agengt\\HELP-PS.md");

        // 2. 创建向量存储（基于内存）
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 3. 创建 tokenizer（HuggingFace BPE Tokenizer，适用于中文和英文）
        Tokenizer tokenizer = new HuggingFaceTokenizer();

        // 4. 配置文档分割器：每段最多 300 token，重叠 30 token
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30, tokenizer);

        // 5. 分割文档为多个 TextSegment
        List<TextSegment> segments = documentSplitter.split(document);

        // 6. 将每个段落写入 Embedding Store（可选：生成 Embedding 后再写入）
        for (TextSegment segment : segments) {
            System.out.println("---------- Segment Start ----------");
            System.out.println(segment.text());
            System.out.println("----------- Segment End -----------\n");

            // 模拟写入 Embedding Store（实际需要调用模型生成 Embedding）
            //embeddingStore.add(segment);
        }

        // 7. 输出总段落数
        System.out.println("总共分割出 " + segments.size() + " 个段落。");
    }

    @Test
    public void testTokenCount() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
//计算 token 长度
//        QwenTokenizer tokenizer = new QwenTokenizer(System.getenv("DASH_SCOPE_API_KEY"),
//                "qwen-max");
        HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();
        int count = tokenizer.estimateTokenCountInMessage(userMessage);
        System.out.println("token长度：" + count);
    }
}
