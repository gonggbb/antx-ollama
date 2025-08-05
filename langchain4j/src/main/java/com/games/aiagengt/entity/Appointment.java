package com.games.aiagengt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Appointment
 * @PackageName com.games.aiagengt.entity
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/5/29 下午4:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String idCard;
    private String department;
    private String date;
    private String time;
    private String doctorName;
}