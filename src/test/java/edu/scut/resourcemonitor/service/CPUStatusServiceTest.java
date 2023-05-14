package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPULoadEntity;
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
        CPULoadEntity loadEntity = service.getTotalCPULoad();
        System.out.println(loadEntity);
        System.out.println(1 - (1.0 * loadEntity.getIdle() / (loadEntity.getSystem() + loadEntity.getIdle() + loadEntity.getNice() + loadEntity.getIrq() + loadEntity.getIoWait() + loadEntity.getSoftIrq() + loadEntity.getSteal() + loadEntity.getUser())));
    }

}
