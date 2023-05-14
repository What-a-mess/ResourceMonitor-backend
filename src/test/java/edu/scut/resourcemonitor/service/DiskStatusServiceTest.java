package edu.scut.resourcemonitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiskStatusServiceTest {
    @Autowired
    DiskStatusService diskStatusService;

    @Autowired
    SystemInfo systemInfo;

    @Test
    public void diskAllStatusTest() {
        List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();
        for (HWDiskStore d : diskStores) {
            System.out.println(d.getModel());
            System.out.println(d.getReadBytes());
            System.out.println(d.getWriteBytes());
            System.out.println("===========");
        }
    }


}
