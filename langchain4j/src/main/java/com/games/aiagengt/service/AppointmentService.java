package com.games.aiagengt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.games.aiagengt.entity.Appointment;
import org.springframework.stereotype.Service;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);

}
