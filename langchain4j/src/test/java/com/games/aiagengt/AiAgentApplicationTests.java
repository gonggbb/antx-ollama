package com.games.aiagengt;

//import dev.langchain4j.model.ollama.OllamaChatModel;

//import dev.langchain4j.community.model.dashscope.QwenChatModel;
//import dev.langchain4j.community.model.dashscope.WanxImageModel;
//import dev.langchain4j.data.image.Image;
//import dev.langchain4j.model.output.Response;

import com.games.aiagengt.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;

import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/*
* 示例 1
* */

/*
*  示例 2
* */
@SpringBootTest
class AiAgentApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("test");
    }


//    @Test
    /*
    * https://docs.langchain4j.dev/get-started
    *
    * OpenAiChatModel model = OpenAiChatModel.builder()
    .baseUrl("http://langchain4j.dev/demo/openai/v1")
    .apiKey("demo")
    .modelName("gpt-4o-mini")
    .build();
    *
    * */
   /* public void testGptDemo() {
        // 创建一个OpenAiChatModel对象，apiKey为demo，modelName为gpt-4o-mini
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        // 调用chat方法，传入参数为"你好"，获取返回值
        String answer = model.chat("你好");
        // 打印返回值
        System.out.println(answer);

    }*/

    /*
     * 整合SpringBoot : openai deepseek qwen:deepseek
     * */
   /* @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testSpringBoot() {
        //向模型提问
        String answer = openAiChatModel.chat("你好");
        System.out.println(answer);
    }*/

   /* @Autowired
    private OllamaChatModel ollamaChatModel;
    *//*
     * ollama接入
     * *//*
    @Test
    public void testOllama() {
        String answer = ollamaChatModel.chat("你好");
        System.out.println(answer);

    }*/
  /*  @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    *//*
     * qwen-max
     * *//*
    public void testQwen() {
        // 调用qwenChatModel的chat方法，传入参数"你好"
        String answer = qwenChatModel.chat("你好");
        // 打印返回的answer
        System.out.println(answer);
    }

    @Test
    public void testDashScopeWanx() {
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASH_SCOPE_API_KEY"))
                .build();
        Response<Image> response = wanxImageModel.generate("在远古时代，天地初开，世间一片混沌。共工与祝融争斗，共工因战败而怒撞不周山。这一撞非同小可，竟使得支撑天空的不周山崩塌，导致天倾西北，地陷东南，洪水泛滥，猛兽肆虐，人民生活困苦不堪。\n" +
                "\n" +
                "目睹此惨状，心怀慈悲的人类之母女娲决定挺身而出，拯救苍生。她首先收集了五色石子——象征五行（金、木、水、火、土）的力量，然后用神力将这些石子熔炼成胶结物质。据说，为了找到足够的五色石，女娲走遍了千山万水，历经艰辛。最终，她成功地把五色石熔炼成了一种神奇的液体，并用它来修补破裂的天空。\n" +
                "\n" +
                "然而，仅仅是修补天空还不够。为了让天空能够稳固，不再发生类似的灾难，女娲斩下了巨龟的四足作为支柱，分别立于大地四方，顶住了天穹。这样，天得以重新稳定，不再倾斜；洪水也渐渐退去，猛兽被驱赶回深山老林中，世界重归平静。\n" +
                "\n" +
                "在这个过程中，女娲付出了巨大的努力和牺牲。传说她在完成这项伟大工程之后，精疲力尽，最终化身成为了山脉，永远守护着这片土地和她的孩子们。");
        System.out.println(response.content().url());
    }*/


    //    @Autowired
//    private OpenAiChatModel openAiChatModel;
    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testSpringBoot() {
        //向模型提问
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String answer = assistant.ask("你好");
        System.out.println(answer);
    }

    @Autowired
    private Assistant assistant;

    /* *
    * assistant配置模型
    *  */
    @Test
    public void testAssistant() {
        String answer = assistant.ask("Hello");
        System.out.println(answer);
    }

}
