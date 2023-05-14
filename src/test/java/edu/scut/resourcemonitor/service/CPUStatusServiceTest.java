package edu.scut.resourcemonitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CPUStatusServiceTest {

    @Autowired
    CPUStatusService service;

    @Test
    public void getCPULoad() {
        double loadEntity = service.getCPUTotalUsage();
        System.out.println(loadEntity);
    }

}
