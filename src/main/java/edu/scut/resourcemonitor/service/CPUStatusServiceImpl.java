package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPUInfo;
import edu.scut.resourcemonitor.entity.CPULoadEntity;
import edu.scut.resourcemonitor.entity.CPUStatus;
import edu.scut.resourcemonitor.util.StateUpdater;
import edu.scut.resourcemonitor.util.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.driver.linux.proc.DiskStats;
import oshi.hardware.CentralProcessor;

@Service
public class CPUStatusServiceImpl implements CPUStatusService, Updatable {

    private final CentralProcessor centralProcessor;
    private final CPUStatus cpuStatus;

    @Autowired
    CPUStatusServiceImpl(SystemInfo systemInfo, StateUpdater updater) {
        this.centralProcessor = systemInfo.getHardware().getProcessor();
        this.cpuStatus = new CPUStatus();
        updater.addUpdateObj(this);
    }

    @Override
    public double getCPUTotalUsage() {
        return cpuStatus.getCPUTotalUsage();
    }

    @Override
    public double[] getCPUCoreUsage() {
        return cpuStatus.getCPUCoreUsage();
    }

    @Override
    public CPUInfo getCPUInfo() {
        CPUInfo cpuInfo = new CPUInfo();
        cpuInfo.setName(centralProcessor.getProcessorIdentifier().getName());
        cpuInfo.setPhysicalProcessorsCnt(centralProcessor.getPhysicalProcessorCount());
        cpuInfo.setLogicalProcessorsCnt(centralProcessor.getLogicalProcessorCount());
        cpuInfo.setBaseFreq(centralProcessor.getProcessorIdentifier().getVendorFreq());
        return cpuInfo;
    }

    @Override
    public void update() {
        int SAMPLE_MILLS = 500;
        cpuStatus.setCPUTotalUsage(centralProcessor.getSystemCpuLoad(SAMPLE_MILLS));
        cpuStatus.setCPUCoreUsage(centralProcessor.getProcessorCpuLoad(SAMPLE_MILLS));
    }
}
