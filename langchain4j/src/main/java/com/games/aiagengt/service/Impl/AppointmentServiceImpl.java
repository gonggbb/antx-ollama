package com.games.aiagengt.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.games.aiagengt.entity.Appointment;
import com.games.aiagengt.mapper.AppointmentMapper;
import com.games.aiagengt.service.AppointmentService;
import org.springframework.stereotype.Service;

/**
 * @ClassName AppointmentServiceImpl
 * @PackageName com.games.aiagengt.service.Impl
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/5/29 下午4:25
 * @Version 1.0
 */

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    /**
     * 查询订单是否存在
     * @param appointment
     * @return
     */
    @Override
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());
        Appointment appointmentDB = baseMapper.selectOne(queryWrapper);
        return appointmentDB;
    }
}
