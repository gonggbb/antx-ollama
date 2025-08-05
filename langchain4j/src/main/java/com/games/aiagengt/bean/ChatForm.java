package com.games.aiagengt.bean;

import lombok.Data;

/**
 * @ClassName ChatForm
 * @PackageName com.games.aiagengt.bean
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/6/20 下午5:52
 * @Version 1.0
 */
@Data
public class ChatForm {
    private Long memoryId;//对话id
    private String message;//用户问
}
