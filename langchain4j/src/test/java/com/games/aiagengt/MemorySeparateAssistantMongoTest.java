package com.games.aiagengt;

import com.games.aiagengt.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
* mongo测试
* */
@SpringBootTest
class MemorySeparateAssistantMongoTest {

    @Autowired
    private Assistant assistant;

    @Test
    public void testMemory2() {
        String answer1 = assistant.ask(1, "我是jjbgo");
        System.out.println(answer1);
        String answer2 = assistant.ask(1, "我是谁");
        System.out.println(answer2);
        String answer3 = assistant.ask(2, "我是谁");
        System.out.println(answer3);

    }

}
