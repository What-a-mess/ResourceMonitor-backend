package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.NetworkIFStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NetworkIFStatusServiceTest {
    @Autowired
    SystemInfo systemInfo;
    @Autowired
    NetworkIFStatusService networkIFStatusService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void simpTest() {
        List<NetworkIF> IFList = systemInfo.getHardware().getNetworkIFs();

        for (NetworkIF IF : IFList) {
            logger.info(IF.getName()+"\tdisplay as:" + IF.getDisplayName() + "\t" + IF.getSpeed());
        }
    }

    @Test
    public void serviceTest() {
        List<NetworkIFStatus> statusList = networkIFStatusService.getAllNetworkIFStatus();
        for (NetworkIFStatus status : statusList) {
           logger.info(status.toString());
        }
    }
}
