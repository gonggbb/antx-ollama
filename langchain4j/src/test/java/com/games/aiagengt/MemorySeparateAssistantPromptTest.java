package com.games.aiagengt;

import com.games.aiagengt.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/*
 * mongo测试
 * */
@SpringBootTest
class MemorySeparateAssistantPromptTest {

    @Autowired
    private Assistant assistant;

    /*系统模版*/
    @Test
    public void testMemory2() {
        /*  String answer1 = assistant.askPrompt(1, "老弟,你是谁");
         *//*  哼哼，我是你的好朋友猪猪侠呀！我们一起守护梦想，打败坏蛋吧！哼哼哈嘿！*//*
        System.out.println(answer1);*/
        String answer2 = assistant.askFromResource(1, "老弟,你是谁");
        System.out.println(answer2);
    }

    /*用户信息*/
    @Test
    public void chatUserMessage() {
        String answer2 = assistant.chatUserMessage("老妹,你是谁");
        System.out.println(answer2);
    }

    /*配置@V*/
    @Test
    public void chatUserMessageV() {
      /*  String answer2 = assistant.chatUserMessageV("老妹,你是谁");
        System.out.println(answer2);*/
        String answer2 = assistant.chatUserMessageV2(1, "我是ggb");
        System.out.println(answer2);

        String answer3 = assistant.chatUserMessageV2(1, "who是ggb");
        System.out.println(answer3);

    }


    @Test
    /**
    * @Description: [系统配置文件传递参数]
    * @Param: []
    * @return: void
    * @Author: games
    * @Date: 下午5:40 2025/5/27
    */
    public void testUserInfo() {
        String answer = assistant.chatSystemMessageV2(1, "我是谁，我多大了", "ggb", 2005);
        System.out.println(answer);
    }

    @Test
    public void testResourceLoading() throws Exception {
        ClassPathResource resource = new ClassPathResource("/prompt-template.txt");
        try (InputStream is = resource.getInputStream()) {
            System.out.println("资源存在，读取成功！");
        } catch (Exception e) {
            System.err.println("资源不存在，请检查路径或构建输出。");
        }
    }


}
