package edu.scut.resourcemonitor.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CPUStatusServiceTest {

    @Autowired
    CPUStatusService service;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    @Order(0)
    public void beforeCPUTest() {
        double totalLoad = service.getCPUTotalUsage();
        Assertions.assertEquals(service.getCPUTotalUsage(), 0, 1e-3);
        logger.info("initial usage: \t" + totalLoad);
    }

    @Test
    @Order(1)
    public void getCPULoad() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        double loadEntity = service.getCPUTotalUsage();
        Assertions.assertTrue(loadEntity > 0);
        logger.info("usage: \t" + loadEntity);
    }

}
