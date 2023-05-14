package edu.scut.resourcemonitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetworkIFStatusServiceTest {
    @Autowired
    SystemInfo systemInfo;

    @Test
    public void simpTest() {
        List<NetworkIF> IFList = systemInfo.getHardware().getNetworkIFs();

        for (NetworkIF IF : IFList) {
            System.out.println(IF.getName()+"\tdisplay as:" + IF.getDisplayName() + "\t" + IF.getSpeed());
        }
    }
}