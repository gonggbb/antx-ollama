package com.games.aiagengt;

import com.games.aiagengt.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/*
* 示例 3
* */
@SpringBootTest
class MemoryAssistantTest {

    @Autowired
    private Assistant assistant;
    @Test
    public void testMemory2() {
        String response = assistant.ask("我叫 jjb");
        System.out.println(response);
        String response1 = assistant.ask("谁是jjb");
        System.out.println(response1);
        }

}
