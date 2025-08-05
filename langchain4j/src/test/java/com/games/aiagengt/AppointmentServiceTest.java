package com.games.aiagengt;

import com.games.aiagengt.entity.Appointment;
import com.games.aiagengt.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName AppointmentServiceTest
 * @PackageName com.games.aiagengt
 * @projectName ai-agengt
 * @Description TODO
 * @Author games
 * @Date 2025/5/29 下午4:31
 * @Version 1.0
 */
@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;
    @Test
    void testGetOne() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("神经");
        appointment.setDate("2025-04-14");
        appointment.setTime("上午");
        Appointment appointmentDB = appointmentService.getOne(appointment);
        System.out.println(appointmentDB);
    }
    @Test
    void testSave() {
        Appointment appointment = new Appointment();
        appointment.setUsername("李四");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("神经");
        appointment.setDate("2025-04-14");
        appointment.setTime("上午");
        appointment.setDoctorName("张医生");
        appointmentService.save(appointment);
    }
    @Test
    void testRemoveById() {
        appointmentService.removeById(1L);
    }
}
