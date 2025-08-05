package com.games.aiagengt;

import com.games.aiagengt.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * mongo测试
 * */
@SpringBootTest
class MemorySeparateAssistantPromptToolTest {

    @Autowired
    private Assistant assistant;


    @Test
    public void testCalculatorTools() {
        String answer = assistant.chatUserMessageV2(1, "1+2等于几，36的平方根是多少？");
        //答案：3，689706.4865
        System.out.println(answer);
        String answer2= assistant.chatUserMessageV2(2, "3+2等于几，475695037565的平方根是多少？");
        //答案：3，689706.4865
        System.out.println(answer2);
    }


}
