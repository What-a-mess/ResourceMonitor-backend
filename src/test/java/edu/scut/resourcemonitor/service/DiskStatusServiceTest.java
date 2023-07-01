package edu.scut.resourcemonitor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DiskStatusServiceTest {
    @Autowired
    DiskStatusService diskStatusService;

    @Autowired
    SystemInfo systemInfo;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void testStarting() {
        logger.info("硬盘测试开始");
    }

    @Test
    public void diskAllStatusTest() {
        List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();
        int index = 0;
        Assertions.assertTrue(diskStores.size() > 0);
        for (HWDiskStore d : diskStores) {
            logger.info("===========");
            logger.info("disk:\t" + index);
            Assertions.assertNotNull(d);
            Assertions.assertNotNull(d.getModel());
            Assertions.assertTrue(d.getReadBytes()>=0);
            Assertions.assertTrue(d.getWriteBytes()>=0);
            logger.info("硬盘型号\t" + d.getModel());
            logger.info("IO Read\t" + String.valueOf(d.getReadBytes()));
            logger.info("IO Write\t" + String.valueOf(d.getWriteBytes()));
            index++;
        }
        logger.info("===========");
    }

    @AfterEach
    public void testEnding() {
        logger.info("硬盘测试结束");
    }


}
