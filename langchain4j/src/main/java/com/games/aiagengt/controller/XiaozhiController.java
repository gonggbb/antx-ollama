package com.games.aiagengt.controller;

import com.games.aiagengt.assistant.Assistant;
import com.games.aiagengt.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @ClassName XiaozhiController
 * @PackageName com.games.aiagengt.controller
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/6/20 下午5:48
 * @Version 1.0
 */
@Tag(name = "医疗AI")
@RestController
@RequestMapping("/ai")
public class XiaozhiController {
    @Autowired
    private Assistant xiaozhiAgent;


    //@Operation(summary = "对话")
    //@PostMapping("/chat")
    //public String chat(@RequestBody ChatForm chatForm) {
    //    return xiaozhiAgent.ask(chatForm.getMemoryId(), chatForm.getMessage());
    //}

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent.ask(chatForm.getMemoryId(), chatForm.getMessage());
    }
}