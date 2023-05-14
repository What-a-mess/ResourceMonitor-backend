package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPUInfo;
import edu.scut.resourcemonitor.entity.CPULoadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Service
public class CPUStatusServiceImpl implements CPUStatusService {
    CentralProcessor centralProcessor;

    @Autowired
    CPUStatusServiceImpl(SystemInfo systemInfo) {
        centralProcessor = systemInfo.getHardware().getProcessor();
    }

    @Deprecated
    private CPULoadEntity rawLoad2Entity(long[] rawCPULoad) {
        CPULoadEntity loadEntity = new CPULoadEntity();
        loadEntity.setUser(rawCPULoad[CentralProcessor.TickType.USER.getIndex()]);
        loadEntity.setSystem(rawCPULoad[CentralProcessor.TickType.SYSTEM.getIndex()]);
        loadEntity.setIdle(rawCPULoad[CentralProcessor.TickType.IDLE.getIndex()]);
        loadEntity.setNice(rawCPULoad[CentralProcessor.TickType.NICE.getIndex()]);
        loadEntity.setIoWait(rawCPULoad[CentralProcessor.TickType.IOWAIT.getIndex()]);
        loadEntity.setIrq(rawCPULoad[CentralProcessor.TickType.IRQ.getIndex()]);
        loadEntity.setSoftIrq(rawCPULoad[CentralProcessor.TickType.SOFTIRQ.getIndex()]);
        loadEntity.setSteal(rawCPULoad[CentralProcessor.TickType.STEAL.getIndex()]);
        return loadEntity;
    }

    @Override
    public double getCPUTotalUsage() {
        return centralProcessor.getSystemCpuLoad(10);
    }

    @Override
    public double[] getCPUCoreUsage() {
        return centralProcessor.getProcessorCpuLoad(10);
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
}
